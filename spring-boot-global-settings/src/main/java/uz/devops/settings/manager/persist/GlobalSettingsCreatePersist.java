package uz.devops.settings.manager.persist;

import lombok.extern.slf4j.Slf4j;
import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.GlobalSettingsExecution;
import uz.devops.settings.manager.GlobalSettingsPersistService;
import uz.devops.settings.manager.data.GlobalSettingsDataManager;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.devops.settings.util.BaseUtils.createFieldName;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.persist
 * @since 12/1/2023 5:00 PM
 */
@Slf4j
public class GlobalSettingsCreatePersist implements GlobalSettingsPersist {

    public static final String NAME = "globalSettingsCreatePersist";
    private final GlobalSettingsExecution globalSettingsExecution;
    private final GlobalSettingsPersistService settingsPersistService;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;

    public GlobalSettingsCreatePersist(PersistBeans beans) {
        this.settingsPersistService = beans.getSettingsPersistService();
        this.globalSettingsExecution = beans.getGlobalSettingsExecution();
        this.globalSettingInfoRepository = beans.getGlobalSettingInfoRepository();
        this.globalSettingFieldsRepository = beans.getGlobalSettingFieldsRepository();
    }

    @Override
    public void persist(List<GlobalSettingsImplementInfo> infos) {
        log.info("global configurations : CREATE : persist start!");
        infos.forEach(info -> determinationInfo(info)
                .ifPresentOrElse(settingInfo -> {
                    List<GlobalSettingsImplementFieldInfo> fields = checkInfoFields(info);
                    if (!fields.isEmpty()) {
                        globalSettingsExecution.getConfigClassInstance(info)
                                .ifPresent(instance -> settingsPersistService.saveFields(instance, settingInfo,
                                        createGlobalSettingImplementInfo(fields)));
                    }
                }, () -> globalSettingsExecution.tryExecute(info)));
        log.info("global configurations : CREATE : persist end!");
    }

    private Optional<GlobalSettingInfo> determinationInfo(GlobalSettingsImplementInfo info) {
        return globalSettingInfoRepository.findTop1ByImplementClass(info.getImplementClass().getName());
    }

    private GlobalSettingsImplementInfo createGlobalSettingImplementInfo(List<GlobalSettingsImplementFieldInfo> fieldInfos) {
        GlobalSettingsImplementInfo implementInfo = new GlobalSettingsImplementInfo();
        implementInfo.setFields(fieldInfos);
        return implementInfo;
    }

    private List<GlobalSettingsImplementFieldInfo> checkInfoFields(GlobalSettingsImplementInfo info) {
        List<GlobalSettingsImplementFieldInfo> fields = new ArrayList<>();
        List<GlobalSettingsImplementFieldInfo> infoFields = info.getFields();
        infoFields.forEach(fieldInfo -> {
            if (!globalSettingFieldsRepository.existsByFieldName(createFieldName(fieldInfo))) {
                fields.add(fieldInfo);
            }
        });
        return fields;
    }

    @Override
    public GlobalSettingsProperty.PersistAuto persist() {
        return GlobalSettingsProperty.PersistAuto.CREATE;
    }

}
