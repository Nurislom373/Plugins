package uz.devops.settings.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import uz.devops.settings.annotation.GlobalSettingReference;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.persist.PersistBeans;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.repository.GlobalSettingTitleRepository;
import uz.devops.settings.util.BaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager
 * @since 12/2/2023 6:39 PM
 */
@Slf4j
public class GlobalSettingsExecution {

    private final Map<String, ConfigurationPropertiesBean> propertiesBeanMap;
    private final GlobalSettingsPersistService globalSettingsPersistService;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private final GlobalSettingTitleRepository globalSettingTitleRepository;

    public GlobalSettingsExecution(PersistBeans persistBeans) {
        propertiesBeanMap = persistBeans.getPropertiesBeanMap();
        globalSettingsPersistService = persistBeans.getSettingsPersistService();
        globalSettingInfoRepository = persistBeans.getGlobalSettingInfoRepository();
        globalSettingFieldsRepository = persistBeans.getGlobalSettingFieldsRepository();
        globalSettingTitleRepository = persistBeans.getGlobalSettingTitleRepository();
    }

    public static GlobalSettingsExecution factory(PersistBeans beans) {
        return new GlobalSettingsExecution(beans);
    }

    public void tryExecute(List<GlobalSettingsImplementInfo> infos) {
        infos.forEach(this::tryExecute);
    }

    public void tryExecute(GlobalSettingsImplementInfo info) {
        getConfigClassInstance(info)
                .ifPresentOrElse(instance -> {
                    if (info.hasParent() && info.hasParentClass()) {
                        globalSettingInfoRepository.findTop1ByImplementClass(info.getParentImplementClass().getName())
                                .ifPresent(settingInfo -> globalSettingsPersistService.save(instance, settingInfo, info));
                    } else {
                        globalSettingsPersistService.save(instance, info);
                    }
                }, () -> log.warn("configClass instance not found!"));
    }

    public Optional<Object> getConfigClassInstance(GlobalSettingsImplementInfo info) {
        Class<?> implementClass = info.getImplementClass();
        if (BaseUtils.checkTypeAnnotationPresent(implementClass, GlobalSettingReference.class)) {
            return Optional.ofNullable(findConfigClassInstanceWithReferenceAnnotation(implementClass));
        } else {
            List<String> paths = innerClassesPath(implementClass.getName());
            if (!paths.isEmpty()) {
                Object instance = findMatchDeclaredClassName(paths.get(0));
                return Optional.of(getInfoInstanceWithLoop(paths, instance));
            }
        }
        return Optional.empty();
    }

    private Object getInfoInstanceWithLoop(List<String> paths, Object instance) {
        Object nextInstance = instance;
        for (int i = 1; i < paths.size(); i++) {
            if (Objects.nonNull(nextInstance)) {
                Object infoInstance = getInfoInstance(nextInstance, paths.get(i));
                if (Objects.nonNull(infoInstance)) {
                    nextInstance = infoInstance;
                } else {
                    nextInstance = null;
                }
            }
        }
        return nextInstance;
    }

    private Object getInfoInstance(Object instance, String name) {
        Field[] declaredFields = instance.getClass().getDeclaredFields();
        Object pathInstance = null;
        for (Field declaredField : declaredFields) {
            if (Objects.equals(declaredField.getType().getName(), name)) {
                pathInstance = GlobalSettingsDataUtils.getFieldValueV2(declaredField, instance);
                break;
            }
        }
        return pathInstance;
    }

    private Object findMatchDeclaredClassName(String declaredClassName) {
        return propertiesBeanMap.values().parallelStream().map(ConfigurationPropertiesBean::getInstance)
                .filter(instance -> {
                    return Objects.equals(instance.getClass().getName(), declaredClassName);
                }).findFirst().orElse(findConfigurationsWithFields(declaredClassName));
    }

    private Object findConfigClassInstanceWithReferenceAnnotation(Class<?> implementClass) {
        try {
            GlobalSettingReference annotation = BaseUtils.getAnnotationWithType(implementClass, GlobalSettingReference.class);
            Optional<ConfigurationPropertiesBean> optional = getConfigurationPropertiesBean(annotation);
            if (optional.isPresent()) {
                Object instance = optional.get().getInstance();
                return PropertyUtils.getProperty(instance, annotation.propertyPath());
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Optional<ConfigurationPropertiesBean> getConfigurationPropertiesBean(GlobalSettingReference annotation) {
        return propertiesBeanMap.values().parallelStream().filter(configurationPropertiesBean -> {
            return Objects.equals(configurationPropertiesBean.getInstance().getClass().getName(),
                    annotation.configClass().getName());
        }).findFirst();
    }

    private Object findConfigurationsWithFields(String declaredClassName) {
        for (ConfigurationPropertiesBean propertiesBean : propertiesBeanMap.values()) {
            Object instance = propertiesBean.getInstance();
            Field[] declaredFields = instance.getClass().getDeclaredFields();

            for (Field field : declaredFields) {
                if (Objects.equals(field.getType().getName(), declaredClassName)) {
                    try {
                        return PropertyUtils.getProperty(instance, field.getName());
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private static List<String> innerClassesPath(String name) {
        List<String> paths = new CopyOnWriteArrayList<>();
        int count = getCharCount(name, '$');
        int lastIndex = 0;
        for (int i = 0; i < count; i++) {
            int delimIndex;
            if (lastIndex == 0) {
                delimIndex = name.indexOf("$");
            } else {
                delimIndex = name.indexOf("$", lastIndex + 1);
            }
            paths.add(name.substring(0, delimIndex));
            lastIndex = delimIndex;
        }
        paths.add(name);
        return paths;
    }

    private static int getCharCount(String var, Character delim) {
        int count = 0;
        for (Character c : var.toCharArray()) {
            if (Objects.equals(c, delim)) {
                count++;
            }
        }
        return count;
    }


}
