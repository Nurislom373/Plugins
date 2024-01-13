package uz.devops.settings.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.settings.adapter.DefaultConfigurationFactoryAdapter;
import uz.devops.settings.converter.GlobalSettingValueConverter;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.event.ConfigurationChangedEvent;
import uz.devops.settings.factory.GlobalSettingsConfigurationFactory;
import uz.devops.settings.factory.configuration.AbstractConfigurationFactory;
import uz.devops.settings.factory.configuration.ConfigurationFactory;
import uz.devops.settings.factory.field.FieldFactory;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.service.context.change.ConfigurationChangeService;
import uz.devops.settings.service.dto.FieldCreateDTO;
import uz.devops.settings.service.dto.UpdateField;
import uz.devops.settings.service.dto.configuration.Configuration;
import uz.devops.settings.service.dto.configuration.Field;
import uz.devops.settings.util.BaseUtils;
import uz.devops.settings.value.GlobalSettingValue;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration
 * @since 11/23/2023 4:21 PM
 */
@Slf4j
@Service
public class DefaultConfigurationService implements ConfigurationService {

    private static final String SUCCESS_KEY = "success";
    private final ObjectMapper objectMapper = BaseUtils.OBJECT_MAPPER;
    private static final Map<String, Object> DEFAULT_STATUS = new HashMap<>() {{
        put("message", "successfully updated!");
        put("success", true);
    }};

    private final FieldFactory fieldFactory;
    private final ConfigurationChangeService configurationChangeService;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private final DefaultConfigurationFactoryAdapter configurationFactoryAdapter;
    private final GlobalSettingsConfigurationFactory settingsConfigurationFactory;

    public DefaultConfigurationService(FieldFactory configurationFieldFactory, ConfigurationChangeService configurationChangeService,
                                       GlobalSettingsConfigurationFactory settingsConfigurationFactory, GlobalSettingInfoRepository globalSettingInfoRepository,
                                       GlobalSettingFieldsRepository globalSettingFieldsRepository, DefaultConfigurationFactoryAdapter configurationFactoryAdapter) {
        this.fieldFactory = configurationFieldFactory;
        this.configurationChangeService = configurationChangeService;
        this.settingsConfigurationFactory = settingsConfigurationFactory;
        this.globalSettingInfoRepository = globalSettingInfoRepository;
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
        this.configurationFactoryAdapter = configurationFactoryAdapter;
    }

    /**
     * (non-Javadoc)
     *
     * @see ConfigurationService#getConfigurations()
     */
    @Override
    @Transactional
    public List<Configuration> getConfigurations() {
        return prepareGlobalConfigurations();
    }

    /**
     * (non-Javadoc)
     *
     * @see ConfigurationService#getGlobalConfigurationsWithFields()
     */
    @Override
    @Transactional
    public List<Configuration> getGlobalConfigurationsWithFields() {
        return prepareGlobalConfigurationsWithFields();
    }

    /**
     * (non-Javadoc)
     *
     * @see ConfigurationService#getConfigurationFields(Long)
     */
    @Override
    @Transactional
    public List<Field> getConfigurationFields(Long infoId) {
        return tryGetConfigurationFields(getGlobalSettingInfo(infoId));
    }

    /**
     * (non-Javadoc)
     *
     * @see ConfigurationService#updateFields(List)
     */
    @Override
    public Object updateFields(List<UpdateField> updateFields) {
        AtomicReference<Map<String, Object>> reference = new AtomicReference<>(DEFAULT_STATUS);
        if (!existFieldsId(updateFields)) {
            reference.set(createFailStatus("one or more configuration field not found by id!"));
            return reference.getAcquire();
        }
        return updateFields(updateFields, reference);
    }

    /**
     * (non-Javadoc)
     *
     * @see ConfigurationService#updateField(UpdateField)
     */
    @Override
    public Object updateField(UpdateField updateField) {
        AtomicReference<Map<String, Object>> reference = new AtomicReference<>(DEFAULT_STATUS);
        globalSettingFieldsRepository.findById(updateField.getId())
                .ifPresent(field -> settingsConfigurationFactory.getFieldInfo(field.getFieldName())
                        .ifPresentOrElse(fieldInfo -> {
                            GlobalSettingValue<?> globalSettingValue = fieldInfo.getGlobalSettingValue();
                            String serializedValue = serializeValue(globalSettingValue, updateField.getValue());
                            if (Objects.nonNull(serializedValue)) {
                                try {
                                    configurationChangeService.changeConfigurations(new ConfigurationChangedEvent(this)
                                            .implementClass(fieldInfo.getGlobalSettingsImplementInfo().getImplementClass())
                                            .value(convertSettingValueClass(globalSettingValue, updateField.getValue()))
                                            .valueType(fieldInfo.getGlobalSettingValue().getSettingValueClass())
                                            .fieldName(fieldInfo.getGlobalSettingValue().getIdentifierName()));
                                    updateFieldValue(serializedValue, field);
                                } catch (RuntimeException e) {
                                    reference.set(createFailStatus(e.getMessage()));
                                }
                            } else {
                                reference.set(createFailStatus("value serialize failed!"));
                            }
                        }, () -> log.warn("fieldInfo not found by name! : {}", field.getFieldName())));
        return reference.getAcquire();
    }

    private Object updateFields(List<UpdateField> updateFields, AtomicReference<Map<String, Object>> reference) {
        List<Map<String, Object>> fails = new ArrayList<>();
        checkUpdateFieldResults(updateFields, fails);

        if (!fails.isEmpty()) {
            return fails;
        }
        return reference.getAcquire();
    }

    private GlobalSettingInfo getGlobalSettingInfo(Long infoId) {
        return globalSettingInfoRepository.findById(infoId)
                .orElseThrow(() -> new RuntimeException("Configuration not found!"));
    }

    private List<Field> tryGetConfigurationFields(GlobalSettingInfo globalSettingInfo) {
        return fieldFactory.create(new FieldCreateDTO(globalSettingInfo.getImplementClass(), globalSettingInfo.getId()));
    }

    private List<Configuration> prepareGlobalConfigurations() {
        AbstractConfigurationFactory abstractConfigurationFactory = configurationFactoryAdapter.getAbstractFactory();
        ConfigurationFactory configurationFactory = abstractConfigurationFactory.configurationFactory();
        return tryPrepareGlobalConfigurations(configurationFactory);
    }

    private List<Configuration> tryPrepareGlobalConfigurations(ConfigurationFactory configurationFactory) {
        return globalSettingInfoRepository.findAll().stream()
                .map(configurationFactory::create).collect(Collectors.toList());
    }

    private List<Configuration> prepareGlobalConfigurationsWithFields() {
        return globalSettingInfoRepository.findAll().stream()
                .map(this::createConfiguration).collect(Collectors.toList());
    }

    private Configuration createConfiguration(GlobalSettingInfo globalSettingInfo) {
        return configurationFactoryAdapter.create(globalSettingInfo);
    }

    @SuppressWarnings({"unchecked"})
    private void checkUpdateFieldResults(List<UpdateField> updateFields, List<Map<String, Object>> fails) {
        updateFields.forEach(updateField -> {
            Map<String, Object> result = (Map<String, Object>) updateField(updateField);
            if (!result.containsKey(SUCCESS_KEY) || !((Boolean) result.get(SUCCESS_KEY))) {
                result.put("id", updateField.getId());
                fails.add(result);
            }
        });
    }

    private boolean existFieldsId(List<UpdateField> updateFields) {
        return updateFields.stream().allMatch(updateField -> globalSettingFieldsRepository.existsById(updateField.getId()));
    }

    private void updateFieldValue(String serializedValue, GlobalSettingFields field) {
        field.setFieldValue(serializedValue);
        GlobalSettingFields saved = globalSettingFieldsRepository.save(field);
        log.info("successfully updated field : {}", saved);
    }

    private String serializeValue(GlobalSettingValue<?> settingValue, Object value) {
        try {
            GlobalSettingValueConverter converter = settingValue.getSettingValueConverter();
            value = convertSettingValueClass(settingValue, value);
            return converter.serializeValue(value);
        } catch (Exception ex) {
            log.warn("serializeValue method exception thrown : {}", ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Object convertSettingValueClass(GlobalSettingValue<?> settingValue, Object value) {
        if (!Objects.equals(settingValue.getSettingValueClass(), value.getClass())) {
            value = objectMapper.convertValue(value, settingValue.getSettingValueClass());
        }
        return value;
    }

    private Map<String, Object> createFailStatus(String message) {
        return Map.of("message", message, "success", false);
    }

}
