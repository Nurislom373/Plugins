package uz.devops.settings.factory.configuration;

import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.factory.GenericFactory;
import uz.devops.settings.service.dto.configuration.Configuration;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.configuration
 * @since 12/21/2023 4:42 PM
 */
public interface ConfigurationFactory extends GenericFactory<GlobalSettingInfo, Configuration> {
}
