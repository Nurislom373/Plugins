package uz.devops.settings.service.configuration.value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.enumuration.FieldType;
import uz.devops.settings.service.dto.configuration.Value;
import uz.devops.settings.service.dto.configuration.value.DefaultValue;
import uz.devops.settings.service.dto.configuration.value.ObjectValue;
import uz.devops.settings.util.BaseUtils;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration.value
 * @since 12/4/2023 5:44 PM
 */
@Slf4j
@Service
public class CollectionConfigurationValueStrategy implements ConfigurationValueStrategy {

    private final ApplicationContext applicationContext;
    private final ObjectMapper objectMapper = BaseUtils.OBJECT_MAPPER;

    public CollectionConfigurationValueStrategy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Value create(GlobalSettingFields fields, Class<?> configurationClass) {
        try {
            Class<?> fieldType = springClassLoader(fields.getFieldClassType(), applicationContext);
            var map = objectMapper.readValue(fields.getFieldValue(), fieldType);
            return new ObjectValue(map);
        } catch (JsonProcessingException e) {
            log.warn("deserialize exception thrown : {}", e.getMessage());
            return new DefaultValue(fields.getFieldValue());
        }
    }

    @Override
    public FieldType getKey() {
        return null;
    }
}
