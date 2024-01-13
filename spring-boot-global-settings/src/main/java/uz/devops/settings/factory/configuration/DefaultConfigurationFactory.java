package uz.devops.settings.factory.configuration;

import org.springframework.stereotype.Component;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.factory.title.TitleFactory;
import uz.devops.settings.service.dto.configuration.Configuration;
import uz.devops.settings.service.dto.configuration.DefaultConfiguration;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.configuration
 * @since 12/21/2023 4:43 PM
 */
@Component
public class DefaultConfigurationFactory implements ConfigurationFactory {

    private final TitleFactory titleFactory;

    public DefaultConfigurationFactory(TitleFactory titleFactory) {
        this.titleFactory = titleFactory;
    }

    @Override
    public Configuration create(GlobalSettingInfo globalSettingInfo) {
        return DefaultConfiguration.builder()
                .id(globalSettingInfo.getId())
                .name(globalSettingInfo.getClassName())
                .titles(titleFactory.create(globalSettingInfo.getTitles()))
                .build();
    }

}
