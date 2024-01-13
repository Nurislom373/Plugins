package uz.devops.settings.factory.models;

import lombok.*;
import uz.devops.settings.value.GlobalSettingValue;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GlobalSettingsImplementFieldInfo {

    private final GlobalSettingsImplementInfo globalSettingsImplementInfo;
    private GlobalSettingValue<?> globalSettingValue;
    private List<GlobalSettingsImplementTitleInfo> titles;

    public GlobalSettingsImplementFieldInfo(GlobalSettingsImplementInfo globalSettingsImplementInfo) {
        this.globalSettingsImplementInfo = globalSettingsImplementInfo;
        this.titles = new ArrayList<>();
    }

}
