package uz.devops.settings.service.context.change;

import uz.devops.settings.event.ConfigurationChangedEvent;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.change
 * @since 11/28/2023 4:55 PM
 */
public interface ConfigurationChangeService {

    void changeConfigurations(ConfigurationChangedEvent event);

}
