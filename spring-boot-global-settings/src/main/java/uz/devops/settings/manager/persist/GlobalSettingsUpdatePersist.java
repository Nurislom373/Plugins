package uz.devops.settings.manager.persist;

import lombok.extern.slf4j.Slf4j;
import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.GlobalSettingsExecution;
import uz.devops.settings.manager.GlobalSettingsPersistService;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.persist
 * @since 12/1/2023 5:45 PM
 */
@Slf4j
public class GlobalSettingsUpdatePersist implements GlobalSettingsPersist {

    public static final String NAME = "globalSettingsUpdatePersist";
    private final GlobalSettingsExecution globalSettingsExecution;
    private final GlobalSettingsPersistService settingsPersistService;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;

    public GlobalSettingsUpdatePersist(PersistBeans beans) {
        this.globalSettingsExecution = beans.getGlobalSettingsExecution();
        this.settingsPersistService = beans.getSettingsPersistService();
        this.globalSettingInfoRepository = beans.getGlobalSettingInfoRepository();
        this.globalSettingFieldsRepository = beans.getGlobalSettingFieldsRepository();
    }

    @Override
    public void persist(List<GlobalSettingsImplementInfo> infos) {
        log.info("global configurations : UPDATE : persist start!");
        infos.forEach(info -> determinationInfo(info)
                .ifPresentOrElse(settingInfo -> globalSettingsExecution.getConfigClassInstance(info)
                        .ifPresent(instance -> settingsPersistService.updateFields(instance, settingInfo, info)),
                        () -> globalSettingsExecution.tryExecute(info)));
        log.info("global configurations : UPDATE : persist end!");
    }

    private Optional<GlobalSettingInfo> determinationInfo(GlobalSettingsImplementInfo info) {
        return globalSettingInfoRepository.findTop1ByImplementClass(info.getImplementClass().getName());
    }

    @Override
    public GlobalSettingsProperty.PersistAuto persist() {
        return GlobalSettingsProperty.PersistAuto.UPDATE;
    }
}
