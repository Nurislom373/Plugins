package uz.devops.settings.service.configuration;

import uz.devops.settings.service.dto.UpdateField;
import uz.devops.settings.service.dto.configuration.Configuration;
import uz.devops.settings.service.dto.configuration.Field;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service
 * @since 11/23/2023 4:17 PM
 */
public interface ConfigurationService {

    List<Configuration> getConfigurations();

    List<Configuration> getGlobalConfigurationsWithFields();

    List<Field> getConfigurationFields(Long infoId);

    Object updateFields(List<UpdateField> updateFields);

    Object updateField(UpdateField updateField);

}
