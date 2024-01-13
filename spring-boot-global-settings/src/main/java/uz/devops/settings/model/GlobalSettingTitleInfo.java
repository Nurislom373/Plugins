package uz.devops.settings.model;

import uz.devops.settings.annotation.GlobalSettingTitle;
import uz.devops.settings.factory.models.GlobalSettingsImplementTitleInfo;

public class GlobalSettingTitleInfo {
    private String language;
    private String title;

    public GlobalSettingTitleInfo(GlobalSettingsImplementTitleInfo titleInfo) {
        this.language = titleInfo.getLanguage();
        this.title = titleInfo.getTitle();
    }

    public GlobalSettingTitleInfo(GlobalSettingTitle s) {
    }

    public GlobalSettingTitleInfo() {
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
