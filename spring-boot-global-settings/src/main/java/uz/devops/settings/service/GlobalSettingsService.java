package uz.devops.settings.service;

import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;

import java.util.Map;

public interface GlobalSettingsService {
    Map<String, String> getValues(GlobalSettingsImplementInfo info);

    void setValues(GlobalSettingsImplementInfo info, Map<String, String> settings);
}
