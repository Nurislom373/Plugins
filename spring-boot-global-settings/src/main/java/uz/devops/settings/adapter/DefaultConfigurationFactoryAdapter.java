package uz.devops.settings.adapter;

import org.springframework.stereotype.Service;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.factory.configuration.AbstractConfigurationFactory;
import uz.devops.settings.factory.configuration.ConfigurationFactory;
import uz.devops.settings.factory.configuration.ConfigurationFieldsFactory;
import uz.devops.settings.service.dto.ConfigurationFieldsDTO;
import uz.devops.settings.service.dto.configuration.Configuration;

/**
 * @author Nurislom
 * @see uz.devops.settings.adapter
 * @since 12/21/2023 5:05 PM
 */
@Service
public class DefaultConfigurationFactoryAdapter implements ConfigurationFactoryAdapter {

    private final AbstractConfigurationFactory abstractConfigurationFactory;

    public DefaultConfigurationFactoryAdapter(AbstractConfigurationFactory configurationFactory) {
        this.abstractConfigurationFactory = configurationFactory;
    }

    @Override
    public Configuration create(GlobalSettingInfo globalSettingInfo) {
        ConfigurationFactory configurationFactory = abstractConfigurationFactory.configurationFactory();
        ConfigurationFieldsFactory configurationFieldsFactory = abstractConfigurationFactory.configurationFieldsFactory();
        return configurationFieldsFactory.create(createConfiguration(globalSettingInfo, configurationFactory));
    }

    @Override
    public AbstractConfigurationFactory getAbstractFactory() {
        return this.abstractConfigurationFactory;
    }

    private ConfigurationFieldsDTO createConfiguration(GlobalSettingInfo globalSettingInfo, ConfigurationFactory configurationFactory) {
        return new ConfigurationFieldsDTO(configurationFactory.create(globalSettingInfo), globalSettingInfo.getId(), globalSettingInfo.getImplementClass());
    }

}
