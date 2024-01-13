package uz.devops.settings.manager.data;

import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager
 * @since 11/22/2023 11:56 AM
 */
public interface GlobalSettingsDataManager {

    void execute(List<GlobalSettingsImplementInfo> infos);

}
