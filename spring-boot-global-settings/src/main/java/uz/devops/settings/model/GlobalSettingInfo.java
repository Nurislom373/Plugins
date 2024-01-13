package uz.devops.settings.model;

import lombok.*;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.value.GlobalSettingValue;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSettingInfo {

    private String name;
    private GlobalSettingTitleInfo[] titles;
    private InputType inputType;
    private String fieldType;

    public GlobalSettingInfo(GlobalSettingsImplementFieldInfo fieldInfo) {
        GlobalSettingValue<?> globalSettingValue = fieldInfo.getGlobalSettingValue();
        this.name = globalSettingValue.getName();
        this.titles = fieldInfo.getTitles().stream().map(GlobalSettingTitleInfo::new).toArray(GlobalSettingTitleInfo[]::new);
        this.inputType = globalSettingValue.getInputType();
        this.fieldType = globalSettingValue.getFieldType();
    }

}
