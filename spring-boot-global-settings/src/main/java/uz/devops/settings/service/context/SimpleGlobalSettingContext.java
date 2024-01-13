package uz.devops.settings.service.context;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.settings.annotation.GlobalSettings;
import uz.devops.settings.converter.GlobalSettingValueConverter;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.factory.GlobalSettingsConfigurationFactory;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.GlobalSettingsDataUtils;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.util.BaseUtils;
import uz.devops.settings.value.GlobalSettingValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static uz.devops.settings.util.BaseUtils.isCollection;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context
 * @since 11/25/2023 5:02 PM
 */
@Slf4j
@Service
public class SimpleGlobalSettingContext implements GlobalSettingContext, Ordered {

    private final Map<GSKey, Object> configClassAndInstanceMap = new ConcurrentHashMap<>();
    private final CollectionValueConverterDelegate collectionValueConverter;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private final GlobalSettingsConfigurationFactory settingsConfigurationFactory;

    public SimpleGlobalSettingContext(CollectionValueConverterDelegate collectionValueConverter, GlobalSettingInfoRepository globalSettingInfoRepository,
                                      GlobalSettingFieldsRepository globalSettingFieldsRepository, GlobalSettingsConfigurationFactory settingsConfigurationFactory) {
        this.collectionValueConverter = collectionValueConverter;
        this.globalSettingInfoRepository = globalSettingInfoRepository;
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
        this.settingsConfigurationFactory = settingsConfigurationFactory;
    }

    @Override
    @Transactional
    @SuppressWarnings({"unchecked"})
    public <T> T getConfiguration(Class<T> configClass) {
        GSKey key = new GSKey(configClass);
        if (!configClassAndInstanceMap.containsKey(key)) {
            tryGetConfiguration(key, null);
        }
        return (T) configClassAndInstanceMap.getOrDefault(key, null);
    }

    @Override
    @Transactional
    public <T> T newConfiguration(Class<T> configClass) {
        return tryNewConfiguration(new GSKey(configClass), null);
    }

    @SuppressWarnings({"unchecked"})
    private <T> T tryNewConfiguration(GSKey key, Object parentInstance) {
        try {
            List<GlobalSettingFields> fields = getGlobalSettingFields(key);
            Object instance = createConfigClass(key.getImplementClass(), fields);
            checkParentInstanceAndSetValue(key, parentInstance, instance, false);
            hasGlobalSettingField(instance.getClass().getDeclaredFields())
                    .ifPresent(field -> tryNewConfiguration(new GSKey(field.getType()), instance));
            return (T) instance;
        } catch (Exception e) {
            log.warn("tryNewConfiguration exception thrown : {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void tryGetConfiguration(GSKey key, Object parentInstance) {
        try {
            List<GlobalSettingFields> fields = getGlobalSettingFields(key);

            Object instance;
            if (!configClassAndInstanceMap.containsKey(key)) {
                instance = createConfigClass(key.getImplementClass(), fields);
            } else {
                instance = configClassAndInstanceMap.get(key);
            }

            checkParentInstanceAndSetValue(key, parentInstance, instance, true);

            hasGlobalSettingField(instance.getClass().getDeclaredFields())
                    .ifPresent(field -> tryGetConfiguration(new GSKey(field.getType()), instance));

        } catch (RuntimeException ex) {
            log.warn("exception message : {}", ex.getMessage());
        }
    }

    private void checkParentInstanceAndSetValue(GSKey key, Object parentInstance, Object instance, boolean addContext) {
        if (Objects.nonNull(instance)) {
            if (!configClassAndInstanceMap.containsKey(key) && addContext) {
                configClassAndInstanceMap.put(key, instance);
            }
            if (Objects.nonNull(parentInstance)) {
                BaseUtils.findMatchField(key.getImplementClass(), parentInstance)
                        .ifPresent(field -> {
                            try {
                                PropertyUtils.setProperty(parentInstance, field.getName(), instance);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                log.warn("set property value exception thrown : {}", e.getMessage());
                                e.printStackTrace();
                            }
                        });
            }
        }
    }

    private List<GlobalSettingFields> getGlobalSettingFields(GSKey key) {
        GlobalSettingInfo settingInfo = globalSettingInfoRepository.findTop1ByImplementClass(key.getClassName())
                .orElseThrow(() -> new RuntimeException("Info not found!"));
        return globalSettingFieldsRepository.findAllByInfo_Id(settingInfo.getId());
    }

    private <T> T createConfigClass(Class<T> configClass, List<GlobalSettingFields> fields) {
        try {
            T instance = BeanUtils.instantiateClass(configClass);
            GlobalSettingsImplementInfo implementInfo = settingsConfigurationFactory.getInfo(configClass);
            fillInstanceDeclaredFields(instance, fields, implementInfo);
            return instance;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillInstanceDeclaredFields(Object instance, List<GlobalSettingFields> fields, GlobalSettingsImplementInfo info) {
        List<GlobalSettingsImplementFieldInfo> fieldInfos = info.getFields();
        fieldInfos.forEach(fieldInfo -> {
            findMatchField(fieldInfo, fields)
                    .ifPresentOrElse(globalSettingFields -> setFieldValue(instance, fieldInfo, globalSettingFields),
                            () -> log.warn("GlobalSettingFields not found!"));
        });
    }

    private void setFieldValue(Object instance, GlobalSettingsImplementFieldInfo fieldInfo, GlobalSettingFields field) {
        try {
            GlobalSettingValue<?> globalSettingValue = fieldInfo.getGlobalSettingValue();
            Field settingField = globalSettingValue.getSettingField();
            GlobalSettingValueConverter valueConverter = globalSettingValue.getSettingValueConverter();
            Object value = valueConverter.deserializeValue(field.getFieldValue());
            if (isCollection(value)) {
                value = collectionValueConverter.linkedMapConvertToGenericCollection(value, settingField);
            }
            GlobalSettingsDataUtils.setFieldValue(settingField, instance, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<GlobalSettingFields> findMatchField(GlobalSettingsImplementFieldInfo fieldInfo, List<GlobalSettingFields> fields) {
        return fields.parallelStream().filter(fields1 -> Objects.equals(fields1.getFieldName(),
                        fieldInfo.getGlobalSettingValue().getName())).findFirst();
    }

    private Optional<Field> hasGlobalSettingField(Field[] fields) {
        return Arrays.stream(fields).filter(field -> field.getType()
                .isAnnotationPresent(GlobalSettings.class)).findFirst();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Data
    private static class GSKey {

        private String className;
        private Class<?> implementClass;

        public GSKey(Class<?> implementClass) {
            this.implementClass = implementClass;
            this.className = implementClass.getName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GSKey gsKey = (GSKey) o;

            return Objects.equals(className, gsKey.className);
        }

        @Override
        public int hashCode() {
            return className != null ? className.hashCode() : 0;
        }
    }

}
