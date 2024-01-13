package uz.devops.settings.manager.persist;

import lombok.extern.slf4j.Slf4j;
import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.GlobalSettingsExecution;
import uz.devops.settings.manager.data.GlobalSettingsDataManager;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.repository.GlobalSettingTitleRepository;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.persist
 * @since 12/1/2023 3:04 PM
 */
@Slf4j
public class GlobalSettingsDropCreatePersist implements GlobalSettingsPersist {

    public static final String NAME = "globalSettingsCreateDropPersist";
    private final GlobalSettingsExecution globalSettingsExecution;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private final GlobalSettingTitleRepository globalSettingTitleRepository;

    public GlobalSettingsDropCreatePersist(PersistBeans beans) {
        this.globalSettingsExecution = beans.getGlobalSettingsExecution();
        this.globalSettingInfoRepository = beans.getGlobalSettingInfoRepository();
        this.globalSettingFieldsRepository = beans.getGlobalSettingFieldsRepository();
        this.globalSettingTitleRepository = beans.getGlobalSettingTitleRepository();
    }

    @Override
    public void persist(List<GlobalSettingsImplementInfo> infos) {
        log.info("global configurations : CREATE_DROP : persist start!");
        globalSettingFieldsRepository.deleteAll();
        globalSettingInfoRepository.deleteAll();
        globalSettingTitleRepository.deleteAll();
        globalSettingsExecution.tryExecute(infos);
        log.info("global configurations : CREATE_DROP : persist end!");
    }

    @Override
    public GlobalSettingsProperty.PersistAuto persist() {
        return GlobalSettingsProperty.PersistAuto.DROP_CREATE;
    }

}
