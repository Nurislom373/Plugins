package uz.devops.settings.factory.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.configuration
 * @since 12/21/2023 5:18 PM
 */
@Component
public class DefaultAbstractConfigurationFactory implements AbstractConfigurationFactory {
    
    private final ConfigurationFactory configurationFactory;
    private final ConfigurationFieldsFactory configurationFieldsFactory;

    public DefaultAbstractConfigurationFactory(@Qualifier("defaultConfigurationFactory") ConfigurationFactory configurationFactory,
                                               ConfigurationFieldsFactory configurationFieldsFactory) {
        this.configurationFactory = configurationFactory;
        this.configurationFieldsFactory = configurationFieldsFactory;
    }

    @Override
    public ConfigurationFieldsFactory configurationFieldsFactory() {
        return this.configurationFieldsFactory;
    }

    @Override
    public ConfigurationFactory configurationFactory() {
        return this.configurationFactory;
    }
    
}
