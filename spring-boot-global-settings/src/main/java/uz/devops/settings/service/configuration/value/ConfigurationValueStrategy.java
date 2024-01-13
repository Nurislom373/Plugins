package uz.devops.settings.service.configuration.value;

import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.service.dto.configuration.Value;
import uz.devops.settings.domain.enumuration.FieldType;
import uz.devops.settings.service.dto.projection.GlobalSettingFieldsProjection;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration.value
 * @since 11/23/2023 6:15 PM
 */
public interface ConfigurationValueStrategy {

    Value create(GlobalSettingFields fields, Class<?> configurationClass);

    FieldType getKey();

}
