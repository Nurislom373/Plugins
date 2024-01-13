package uz.devops.settings.model;

public class GlobalSettingsGroupInfo<T> {
    private String name;
    private GlobalSettingTitleInfo[] titles;
    private GlobalSettingInfo[] settings;
    private T data;

    public GlobalSettingsGroupInfo() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GlobalSettingInfo[] getSettings() {
        return this.settings;
    }

    public void setSettings(GlobalSettingInfo[] settings) {
        this.settings = settings;
    }

    public GlobalSettingTitleInfo[] getTitles() {
        return this.titles;
    }

    public void setTitles(GlobalSettingTitleInfo[] titles) {
        this.titles = titles;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
