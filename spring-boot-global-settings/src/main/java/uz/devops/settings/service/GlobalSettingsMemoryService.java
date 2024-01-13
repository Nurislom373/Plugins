package uz.devops.settings.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uz.devops.settings.annotation.GlobalSettings;
import uz.devops.settings.annotation.GlobalSettingsPackage;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;

import java.util.HashMap;
import java.util.Map;

@ConditionalOnProperty(
        prefix = "global-settings",
        name = {"enableDb"},
        havingValue = "false",
        matchIfMissing = true
)
@Component
@GlobalSettings(defaultValue = "Clasdsasdas")
public class GlobalSettingsMemoryService implements GlobalSettingsService {

    private ApplicationContext enabled;

    private final Map<GlobalSettingsImplementInfo, Map<String, String>> settingsMap = new HashMap<>();

    public GlobalSettingsMemoryService() {
    }

    public Map<String, String> getValues(GlobalSettingsImplementInfo info) {
        return this.settingsMap.getOrDefault(info, null);
    }
    public Map<GlobalSettingsImplementInfo, Map<String, String>> getValuesMap(GlobalSettingsImplementInfo info) {
        return this.settingsMap;
    }

    public void setValues(GlobalSettingsImplementInfo info, Map<String, String> settings) {
        this.settingsMap.put(info, settings);
    }
}
