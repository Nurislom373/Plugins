package uz.devops.settings.adapter;

import uz.devops.settings.factory.configuration.AbstractConfigurationFactory;
import uz.devops.settings.factory.configuration.ConfigurationFactory;

/**
 * @author Nurislom
 * @see uz.devops.settings.adapter
 * @since 12/21/2023 6:03 PM
 */
public interface ConfigurationFactoryAdapter extends ConfigurationFactory {

    AbstractConfigurationFactory getAbstractFactory();

}
