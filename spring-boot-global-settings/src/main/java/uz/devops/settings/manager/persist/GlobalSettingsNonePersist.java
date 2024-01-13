package uz.devops.settings.manager.persist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.persist
 * @since 12/1/2023 5:47 PM
 */
@Slf4j
public class GlobalSettingsNonePersist implements GlobalSettingsPersist {

    public static final String NAME = "globalSettingsNonePersist";

    public GlobalSettingsNonePersist(PersistBeans persistBeans) {
    }

    @Override
    public void persist(List<GlobalSettingsImplementInfo> infos) {
        log.info("global configurations : NONE : persist start!");
        // ...
        log.info("global configurations : NONE : persist end!");
    }

    @Override
    public GlobalSettingsProperty.PersistAuto persist() {
        return GlobalSettingsProperty.PersistAuto.NONE;
    }

}
