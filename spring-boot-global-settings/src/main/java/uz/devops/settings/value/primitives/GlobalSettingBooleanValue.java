package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingBooleanValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingBooleanValue extends GlobalSettingValue<Boolean> {

    public GlobalSettingBooleanValue() {
        this.inputType = InputType.CHECKBOX;
        this.fieldType = "BOOLEAN";
        this.settingValueClass = Boolean.class;
        this.globalSettingValueConverter = new GlobalSettingBooleanValueConverter();
    }

}
