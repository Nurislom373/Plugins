package uz.devops.settings.service.configuration.value;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.enumuration.FieldType;
import uz.devops.settings.service.dto.configuration.Value;
import uz.devops.settings.service.dto.configuration.value.DefaultValue;
import uz.devops.settings.service.dto.configuration.value.ObjectValue;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;
import static uz.devops.settings.util.BaseUtils.*;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration.value
 * @since 11/23/2023 7:01 PM
 */
@Slf4j
@Service
public class DefaultConfigurationValueStrategy implements ConfigurationValueStrategy {

    private final ApplicationContext applicationContext;

    public DefaultConfigurationValueStrategy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Value create(GlobalSettingFields fields, Class<?> configurationClass) {
        try {
            return new ObjectValue(OBJECT_MAPPER.readValue(fields.getFieldValue(), getFieldType(fields)));
        } catch (JsonProcessingException e) {
            log.warn("deserialize exception thrown : {}", e.getMessage());
            return new DefaultValue(fields.getFieldValue());
        }
    }

    private Class<?> getFieldType(GlobalSettingFields fields) {
        if (isPrimitiveClass(fields.getFieldClassType())) {
            return primitiveToWrapper(fields.getFieldClassType());
        } else {
            return springClassLoader(fields.getFieldClassType(), applicationContext);
        }
    }

    @Override
    public FieldType getKey() {
        return null;
    }
}
