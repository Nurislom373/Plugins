package uz.devops.settings.manager.persist;

import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.data
 * @since 12/1/2023 3:02 PM
 */
public interface GlobalSettingsPersist {

    void persist(List<GlobalSettingsImplementInfo> infos);

    GlobalSettingsProperty.PersistAuto persist();

}
