package uz.devops.settings.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import uz.devops.settings.service.context.change.ConfigurationChangeService;

/**
 * @author Nurislom
 * @see uz.devops.settings.event
 * @since 11/28/2023 4:53 PM
 */
@Slf4j
@Component
public class ConfigurationChangedEventListener implements ApplicationListener<ConfigurationChangedEvent> {

    private final ConfigurationChangeService configurationChangeService;

    public ConfigurationChangedEventListener(ConfigurationChangeService configurationChangeService) {
        this.configurationChangeService = configurationChangeService;
    }

    @Override
    public void onApplicationEvent(ConfigurationChangedEvent event) {
        log.debug("configuration changed event listen : {}", event);
        configurationChangeService.changeConfigurations(event);
    }

}
