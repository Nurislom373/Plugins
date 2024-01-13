package uz.devops.settings.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;

import java.util.*;

@Service
@Transactional
public class GlobalSettingsDbService implements GlobalSettingsService {

    private final GlobalSettingFieldsRepository globalSettingRepository;

    public GlobalSettingsDbService(GlobalSettingFieldsRepository globalSettingRepository) {
        this.globalSettingRepository = globalSettingRepository;
    }

    public Map<String, String> getValues(GlobalSettingsImplementInfo info) {
//        Map<String, String> result = new HashMap<>();
//        List<GlobalSettingFields> globalSettings = getGlobalSettingsForClass(info);
//        globalSettings.forEach(globalSetting -> result.put(globalSetting.getFieldName(), globalSetting.getFieldValue()));
        return Collections.emptyMap();
    }

    public void setValues(GlobalSettingsImplementInfo info, Map<String, String> settings) {
//        List<GlobalSettingFields> dbGlobalSettings = getGlobalSettingsForClass(info);
//
//        settings.forEach((key, value) -> {
//            dbGlobalSettings.stream().filter(setting -> setting.getFieldName().equals(key)).findFirst().ifPresent(existingSetting -> existingSetting.setFieldValue(value));
//            GlobalSettingFields newSetting = new GlobalSettingFields();
//                newSetting.setFieldName(key);
//                newSetting.setFieldValue(value);
//                dbGlobalSettings.add(newSetting);
//        });
//
//        globalSettingRepository.saveAll(dbGlobalSettings);
    }

    private List<GlobalSettingFields> getGlobalSettingsForClass(GlobalSettingsImplementInfo info) {
//        if (info == null || info.getImplementClass() == null) {
//            return Collections.emptyList();
//        }
//        return globalSettingRepository.findAllByClassName(info.getImplementClass().getSimpleName());
        return Collections.emptyList();
    }

}
