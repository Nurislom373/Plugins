package uz.devops.settings.factory.configuration;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import uz.devops.settings.factory.field.FieldFactory;
import uz.devops.settings.service.dto.ConfigurationFieldsDTO;
import uz.devops.settings.service.dto.configuration.Configuration;
import uz.devops.settings.service.dto.configuration.ConfigurationFields;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.configuration
 * @since 12/21/2023 4:54 PM
 */
@Component
public class DefaultConfigurationFieldsFactory implements ConfigurationFieldsFactory {

    private final FieldFactory configurationFieldFactory;

    public DefaultConfigurationFieldsFactory(FieldFactory configurationFieldFactory) {
        this.configurationFieldFactory = configurationFieldFactory;
    }

    @Override
    public Configuration create(ConfigurationFieldsDTO dto) {
        ConfigurationFields configurationFields = new ConfigurationFields(configurationFieldFactory.create(dto));
        BeanUtils.copyProperties(dto.getConfiguration(), configurationFields);
        return configurationFields;
    }

}
