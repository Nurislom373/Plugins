package uz.devops.settings.factory.configuration;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.configuration
 * @since 12/21/2023 5:07 PM
 */
public interface AbstractConfigurationFactory {

    ConfigurationFieldsFactory configurationFieldsFactory();

    ConfigurationFactory configurationFactory();

}
