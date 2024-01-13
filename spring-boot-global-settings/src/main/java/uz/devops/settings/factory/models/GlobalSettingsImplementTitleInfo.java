package uz.devops.settings.factory.models;

import lombok.*;
import uz.devops.settings.annotation.GlobalSettingTitle;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSettingsImplementTitleInfo {
    private String language;
    private String title;

    public GlobalSettingsImplementTitleInfo(GlobalSettingTitle s) {
        this.language = s.language();
        this.title = s.title();
    }

}
