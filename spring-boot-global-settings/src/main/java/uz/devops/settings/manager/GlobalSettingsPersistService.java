package uz.devops.settings.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.converter.GlobalSettingValueConverter;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.domain.GlobalSettingTitle;
import uz.devops.settings.factory.GlobalSettingFactory;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementTitleInfo;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingTitleRepository;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static uz.devops.settings.manager.GlobalSettingsDataUtils.getFieldValueV2;
import static uz.devops.settings.util.BaseUtils.createFieldName;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager
 * @since 11/22/2023 2:02 PM
 */
@Slf4j
@Service
public class GlobalSettingsPersistService {

    private final GlobalSettingFactory globalSettingFactory;
    private final GlobalSettingsProperty globalSettingsProperty;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private final GlobalSettingTitleRepository globalSettingTitleRepository;

    public GlobalSettingsPersistService(GlobalSettingsProperty globalSettingsProperty, GlobalSettingFieldsRepository globalSettingFieldsRepository,
                                        GlobalSettingFactory globalSettingFactory, GlobalSettingTitleRepository globalSettingTitleRepository) {
        this.globalSettingsProperty = globalSettingsProperty;
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
        this.globalSettingFactory = globalSettingFactory;
        this.globalSettingTitleRepository = globalSettingTitleRepository;
    }

    public void save(Object instance, GlobalSettingsImplementInfo info) {
        GlobalSettingInfo globalSettingInfo = globalSettingFactory.createGlobalSettingInfo(null, info);
        saveFields(instance, globalSettingInfo, info);
    }

    public void save(Object instance, GlobalSettingInfo parent, GlobalSettingsImplementInfo info) {
        GlobalSettingInfo globalSettingInfo = globalSettingFactory.createGlobalSettingInfo(instance, parent);
        saveFields(instance, globalSettingInfo, info);
    }

    public void saveFields(Object instance, GlobalSettingInfo settingInfo, GlobalSettingsImplementInfo info) {
        List<GlobalSettingsImplementFieldInfo> fields = info.getFields();
        Arrays.stream(instance.getClass().getDeclaredFields())
                .forEach(field -> matchField(field, fields)
                        .ifPresentOrElse(fieldInfo -> trySave(instance, field, fieldInfo, settingInfo),
                                () -> log.warn("Match field not found!")));
    }

    @Transactional
    public void updateFields(Object instance, GlobalSettingInfo settingInfo, GlobalSettingsImplementInfo info) {
        List<GlobalSettingsImplementFieldInfo> fields = info.getFields();
        Arrays.stream(instance.getClass().getDeclaredFields())
                .forEach(field -> matchField(field, fields)
                        .ifPresentOrElse(fieldInfo -> tryUpdate(instance, field, fieldInfo, settingInfo),
                                () -> log.warn("Match field not found!")));
    }

    private void tryUpdate(Object instance, Field field, GlobalSettingsImplementFieldInfo fieldInfo, GlobalSettingInfo settingInfo) {
        globalSettingFieldsRepository.findTop1ByInfo_IdAndFieldName(settingInfo.getId(), createFieldName(fieldInfo))
                .ifPresentOrElse(fields -> {
                    try {
                        boolean updateValue = false;
                        boolean updateTitles = tryUpdateTitles(fieldInfo, fields); // update titles
                        GlobalSettingValueConverter converter = fieldInfo.getGlobalSettingValue().getSettingValueConverter();
                        Object newFieldValue = PropertyUtils.getProperty(instance, field.getName());
                        if (Objects.nonNull(newFieldValue)) {
                            String serializedValue = converter.serializeValue(newFieldValue);
                            if (!Objects.equals(serializedValue, fields.getFieldValue())) {
                                fields.setFieldValue(serializedValue);
                                updateValue = true;
                            }
                        } else if (!globalSettingsProperty.getIgnoreNullFields()) {
                            fields.setFieldValue(null);
                            updateValue = true;
                        }
                        if (updateTitles || updateValue) {
                            globalSettingFieldsRepository.save(fields);
                        }
                    } catch (Exception e) {
                        log.warn("tryUpdate : exception thrown : {}", e.getMessage());
                        e.printStackTrace();
                    }
                }, () -> trySave(instance, field, fieldInfo, settingInfo));
    }

    private void trySave(Object instance, Field field, GlobalSettingsImplementFieldInfo fieldInfo, GlobalSettingInfo settingInfo) {
        try {
            GlobalSettingFields globalSettingFields = globalSettingFactory.createGlobalSettingFields(fieldInfo, settingInfo);
            GlobalSettingValueConverter valueConverter = fieldInfo.getGlobalSettingValue().getSettingValueConverter();
            Object fieldValue = getFieldValueV2(field, instance);
            if (Objects.nonNull(fieldValue)) {
                globalSettingFields.setFieldValue(valueConverter.serializeValue(fieldValue));
            }
            if (!(globalSettingsProperty.getIgnoreNullFields() && Objects.isNull(globalSettingFields.getFieldValue()))) {
                globalSettingFieldsRepository.save(globalSettingFields);
            }
//            if (globalSettingsProperty.getIgnoreNullFields()) {
//                if (Objects.nonNull(globalSettingFields.getFieldValue())) {
//                }
//            } else {
//                globalSettingFieldsRepository.save(globalSettingFields);
//            }
        } catch (Exception e) {
            log.warn("trySave : exception thrown : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveFieldValue(GlobalSettingFields globalSettingFields, GlobalSettingValueConverter valueConverter, Object fieldValue) throws Exception {
        globalSettingFields.setFieldValue(valueConverter.serializeValue(fieldValue));
        globalSettingFieldsRepository.save(globalSettingFields);
    }

    private boolean tryUpdateTitles(GlobalSettingsImplementFieldInfo fieldInfo, GlobalSettingFields settingFields) {
        List<GlobalSettingTitle> titles = settingFields.getTitles();
        if (Objects.isNull(fieldInfo.getTitles()) || fieldInfo.getTitles().isEmpty()) {
            if (!titles.isEmpty()) {
                globalSettingTitleRepository.deleteAll(titles);
                settingFields.setTitles(null);
            }
        } else {
            List<GlobalSettingTitle> globalSettingTitles = globalSettingFactory.createGlobalSettingTitles(fieldInfo.getTitles());
            if (!titles.isEmpty()) {
                globalSettingTitleRepository.deleteAll(titles);
                settingFields.setTitles(null);
            }
            if (!globalSettingTitles.isEmpty()) {
                settingFields.setTitles(globalSettingTitles);
                return true;
            }
        }
        return false;
    }

    private List<GlobalSettingsImplementTitleInfo> filterGlobalSettingTitle(GlobalSettingsImplementFieldInfo fieldInfo) {
        return fieldInfo.getTitles().stream().filter(titleInfo -> {
            return (Objects.nonNull(titleInfo.getLanguage()) && !titleInfo.getLanguage().isBlank()) &&
                    (Objects.nonNull(titleInfo.getTitle()) && !titleInfo.getTitle().isBlank());
        }).collect(Collectors.toList());
    }

    private Optional<GlobalSettingsImplementFieldInfo> matchField(Field field, List<GlobalSettingsImplementFieldInfo> fields) {
        return fields.parallelStream().filter(fieldInfo -> Objects.equals(field.getName(),
                        fieldInfo.getGlobalSettingValue().getIdentifierName()))
                .findFirst();
    }

}
