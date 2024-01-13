package uz.devops.settings.manager.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.GlobalSettingsPersistService;
import uz.devops.settings.manager.persist.GlobalSettingsPersistDelegate;
import uz.devops.settings.manager.persist.PersistBeans;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.repository.GlobalSettingTitleRepository;

import java.util.List;
import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager
 * @since 11/22/2023 11:57 AM
 */
@Slf4j
@Service
public class SimpleGlobalSettingsDataManager implements GlobalSettingsDataManager {

    private final Map<String, ConfigurationPropertiesBean> propertiesBeanMap;
    private final GlobalSettingsPersistService globalSettingsPersistService;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;
    private final GlobalSettingTitleRepository globalSettingTitleRepository;
    private final GlobalSettingsPersistDelegate globalSettingsPersistDelegate;

    public SimpleGlobalSettingsDataManager(ApplicationContext applicationContext, GlobalSettingInfoRepository globalSettingInfoRepository,
                                           GlobalSettingsPersistService globalSettingObjectService, GlobalSettingFieldsRepository globalSettingFieldsRepository,
                                           GlobalSettingTitleRepository globalSettingTitleRepository, GlobalSettingsPersistDelegate globalSettingsPersistDelegate) {
        this.propertiesBeanMap = ConfigurationPropertiesBean.getAll(applicationContext);
        this.globalSettingInfoRepository = globalSettingInfoRepository;
        this.globalSettingsPersistService = globalSettingObjectService;
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
        this.globalSettingTitleRepository = globalSettingTitleRepository;
        this.globalSettingsPersistDelegate = globalSettingsPersistDelegate;
    }

    @Override
    public void execute(List<GlobalSettingsImplementInfo> infos) {
        globalSettingsPersistDelegate.execute(infos, getPersistBeans());
    }

    private PersistBeans getPersistBeans() {
        return new PersistBeans(globalSettingInfoRepository, globalSettingFieldsRepository, globalSettingTitleRepository,
                globalSettingsPersistService, propertiesBeanMap);
    }

}
