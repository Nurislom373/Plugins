package uz.devops.settings.manager.persist;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import uz.devops.settings.manager.GlobalSettingsExecution;
import uz.devops.settings.manager.GlobalSettingsPersistService;
import uz.devops.settings.manager.data.GlobalSettingsDataManager;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.repository.GlobalSettingTitleRepository;

import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.persist
 * @since 12/1/2023 7:28 PM
 */
@Getter
public class PersistBeans {

    private GlobalSettingsExecution globalSettingsExecution;
    private GlobalSettingInfoRepository globalSettingInfoRepository;
    private GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private GlobalSettingTitleRepository globalSettingTitleRepository;
    private GlobalSettingsPersistService settingsPersistService;
    private Map<String, ConfigurationPropertiesBean> propertiesBeanMap;

    public PersistBeans(GlobalSettingInfoRepository globalSettingInfoRepository, GlobalSettingFieldsRepository globalSettingFieldsRepository,
                        GlobalSettingTitleRepository globalSettingTitleRepository, GlobalSettingsPersistService settingsPersistService,
                        Map<String, ConfigurationPropertiesBean> propertiesBeanMap) {
        this.globalSettingInfoRepository = globalSettingInfoRepository;
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
        this.globalSettingTitleRepository = globalSettingTitleRepository;
        this.settingsPersistService = settingsPersistService;
        this.propertiesBeanMap = propertiesBeanMap;
    }

    public PersistBeans globalSettingsExecution(GlobalSettingsExecution settingsExecution) {
        this.globalSettingsExecution = settingsExecution;
        return this;
    }

}
