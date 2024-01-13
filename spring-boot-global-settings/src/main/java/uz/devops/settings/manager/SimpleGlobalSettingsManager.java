package uz.devops.settings.manager;

import org.springframework.stereotype.Service;
import uz.devops.settings.converter.GlobalSettingValueConverter;
import uz.devops.settings.factory.GlobalSettingsConfigurationFactory;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.model.GlobalSettingInfo;
import uz.devops.settings.model.GlobalSettingTitleInfo;
import uz.devops.settings.model.GlobalSettingsGroupInfo;
import uz.devops.settings.service.GlobalSettingsDbService;
import uz.devops.settings.value.GlobalSettingValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SimpleGlobalSettingsManager {

    private final GlobalSettingsDbService globalSettingsDbService;
    private final GlobalSettingsConfigurationFactory globalSettingsFactory;

    public SimpleGlobalSettingsManager(GlobalSettingsDbService globalSettingsDbService, GlobalSettingsConfigurationFactory globalSettingsFactory) {
        this.globalSettingsDbService = globalSettingsDbService;
        this.globalSettingsFactory = globalSettingsFactory;
    }

    public GlobalSettingsGroupInfo<?>[] getInfos() throws Exception {
        List<GlobalSettingsImplementInfo> infos = globalSettingsFactory.getInfos();
        List<GlobalSettingsGroupInfo<?>> result = new ArrayList<>();

        for (GlobalSettingsImplementInfo info : infos) {
            result.add(getInfo(info.getImplementClass()));
        }

        return (GlobalSettingsGroupInfo<?>[]) result.toArray(new GlobalSettingsGroupInfo[0]);
    }

    public <T> GlobalSettingsGroupInfo<T> getInfo(Class<T> settingClass) throws Exception {
        GlobalSettingsImplementInfo info = globalSettingsFactory.getInfo(settingClass);
        if (info == null) {
            return null;
        } else {
            GlobalSettingsGroupInfo<T> groupInfo = new GlobalSettingsGroupInfo<>();
            groupInfo.setName(info.getName());
            GlobalSettingTitleInfo[] titles = info.getTitles().stream().map(GlobalSettingTitleInfo::new).toArray(GlobalSettingTitleInfo[]::new);
            groupInfo.setTitles(titles);
            GlobalSettingInfo[] settings = info.getFields().stream().map(GlobalSettingInfo::new).toArray(GlobalSettingInfo[]::new);
            groupInfo.setSettings(settings);
            T data = get(settingClass);
            groupInfo.setData(data);
            return groupInfo;
        }
    }

    public <T> T get(Class<T> settingClass) throws Exception {
        GlobalSettingsImplementInfo info = globalSettingsFactory.getInfo(settingClass);
        if (info == null) {
            return null;
        } else {
            T instance = settingClass.newInstance();
            Map<String, String> settings = globalSettingsDbService.getValues(info);
            if (settings != null) {

                for (GlobalSettingsImplementFieldInfo field : info.getFields()) {
                    setObjectFieldValue(settings, field, instance);
                }

            }
            return instance;
        }
    }

    private static void setObjectFieldValue(Map<String, String> settings, GlobalSettingsImplementFieldInfo fieldInfo, Object obj) throws Exception {
        GlobalSettingValue<?> globalSettingValue = fieldInfo.getGlobalSettingValue();
        String settingStringValue = (String) settings.getOrDefault(globalSettingValue.getName(), globalSettingValue.getDefaultValue());
        Field field = fieldInfo.getGlobalSettingValue().getSettingField();
        GlobalSettingValueConverter converter = globalSettingValue.getSettingValueConverter();
        Object value = converter.deserializeValue(settingStringValue);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public void save(Object settings) throws Exception {
        if (settings != null) {
            GlobalSettingsImplementInfo info = globalSettingsFactory.getInfo(settings.getClass());
            if (info != null) {
                Map<String, String> settingsMap = new HashMap<>();

                for (GlobalSettingsImplementFieldInfo field : info.getFields()) {
                    String key = field.getGlobalSettingValue().getName();
                    String stringValue = getFieldStringValue(field, settings);
                    settingsMap.put(key, stringValue);
                }

                globalSettingsDbService.setValues(info, settingsMap);
            }
        }
    }

    private static String getFieldStringValue(GlobalSettingsImplementFieldInfo fieldInfo, Object obj) throws Exception {
        Field field = fieldInfo.getGlobalSettingValue().getSettingField();
        field.setAccessible(true);
        Object fieldValue = field.get(obj);
        GlobalSettingValueConverter converter = fieldInfo.getGlobalSettingValue().getSettingValueConverter();
        return converter.serializeValue(fieldValue);
    }
}
