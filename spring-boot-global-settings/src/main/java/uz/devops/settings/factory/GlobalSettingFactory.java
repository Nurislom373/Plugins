package uz.devops.settings.factory;

import org.springframework.stereotype.Service;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.domain.GlobalSettingTitle;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementTitleInfo;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.repository.GlobalSettingTitleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory
 * @since 11/18/2023 10:53 PM
 */
@Service
public class GlobalSettingFactory {

    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingTitleRepository globalSettingTitleRepository;

    public GlobalSettingFactory(GlobalSettingInfoRepository globalSettingInfoRepository, GlobalSettingTitleRepository globalSettingTitleRepository) {
        this.globalSettingInfoRepository = globalSettingInfoRepository;
        this.globalSettingTitleRepository = globalSettingTitleRepository;
    }

    public GlobalSettingInfo createGlobalSettingInfo(GlobalSettingInfo settingInfo, GlobalSettingsImplementInfo implementInfo) {
        GlobalSettingInfo globalSettingInfo = getGlobalSettingInfo(settingInfo, implementInfo);
        globalSettingInfo.setTitles(createGlobalSettingTitles(implementInfo.getTitles()));
        globalSettingInfoRepository.save(globalSettingInfo);
        return globalSettingInfo;
    }

    public GlobalSettingInfo createGlobalSettingInfo(Object info, GlobalSettingInfo settingInfo) {
        GlobalSettingInfo globalSettingInfo = getGlobalSettingInfo(info, settingInfo);
        globalSettingInfoRepository.save(globalSettingInfo);
        return globalSettingInfo;
    }

    public GlobalSettingFields createGlobalSettingFields(GlobalSettingsImplementFieldInfo fieldInfo, GlobalSettingInfo settingInfo) {
        return createGlobalSettingField(fieldInfo, settingInfo);
    }

    public GlobalSettingsImplementInfo createImplementInfo(Class<?> clazz, List<GlobalSettingsImplementInfo> infos) {
        GlobalSettingsImplementInfo globalSettingsImplementInfo = new GlobalSettingsImplementInfo();
        globalSettingsImplementInfo.setName(clazz.getSimpleName());
        globalSettingsImplementInfo.setImplementClass(clazz);
        infos.add(globalSettingsImplementInfo);
        return globalSettingsImplementInfo;
    }

    private GlobalSettingFields createGlobalSettingField(GlobalSettingsImplementFieldInfo fieldInfo, GlobalSettingInfo settingInfo) {
        GlobalSettingFields globalSetting = new GlobalSettingFields();
        globalSetting.setTitles(createGlobalSettingTitles(fieldInfo.getTitles()));
        globalSetting.setFieldName(fieldInfo.getGlobalSettingValue().getName());
        globalSetting.setFieldType(fieldInfo.getGlobalSettingValue().getFieldType());
        globalSetting.setInputType(fieldInfo.getGlobalSettingValue().getInputType());
        globalSetting.setFieldClassType(fieldInfo.getGlobalSettingValue().getSettingField().getType().getName());
        globalSetting.setInfo(settingInfo);
        return globalSetting;
    }

    private GlobalSettingInfo getGlobalSettingInfo(Object info, GlobalSettingInfo settingInfo) {
        GlobalSettingInfo globalSettingInfo = new GlobalSettingInfo();
        globalSettingInfo.setImplementClass(info.getClass().getName());
        globalSettingInfo.setClassName(info.getClass().getSimpleName());
        if (Objects.nonNull(settingInfo)) {
            globalSettingInfo.setParentId(settingInfo.getId());
        }
        return globalSettingInfo;
    }

    private GlobalSettingInfo getGlobalSettingInfo(GlobalSettingInfo settingInfo, GlobalSettingsImplementInfo implementInfo) {
        GlobalSettingInfo globalSettingInfo = new GlobalSettingInfo();
        globalSettingInfo.setImplementClass(implementInfo.getImplementClass().getName());
        globalSettingInfo.setClassName(implementInfo.getImplementClass().getSimpleName());
        if (Objects.nonNull(settingInfo)) {
            globalSettingInfo.setParentId(settingInfo.getId());
        }
        return globalSettingInfo;
    }

    public List<GlobalSettingTitle> createGlobalSettingTitles(List<GlobalSettingsImplementTitleInfo> titles) {
        List<GlobalSettingTitle> globalSettingTitles;
        if (Objects.nonNull(titles) && !titles.isEmpty()) {
            globalSettingTitles = titles.stream().map(this::createTitle)
                    .collect(Collectors.toList());
        } else {
            globalSettingTitles = Collections.emptyList();
        }
        return globalSettingTitles;
    }

    private GlobalSettingTitle createTitle(GlobalSettingsImplementTitleInfo title) {
        GlobalSettingTitle globalSettingTitle = new GlobalSettingTitle();
        globalSettingTitle.setTitle(title.getTitle());
        globalSettingTitle.setLanguage(title.getLanguage());
        return globalSettingTitleRepository.save(globalSettingTitle);
    }

}
