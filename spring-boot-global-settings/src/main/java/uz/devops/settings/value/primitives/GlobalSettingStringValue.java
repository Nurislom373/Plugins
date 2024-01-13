package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingStringValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingStringValue extends GlobalSettingValue<String> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "TEXT";

    public GlobalSettingStringValue() {
        this.inputType = InputType.INPUT_TEXT;
        this.fieldType = "TEXT";
        this.settingValueClass = String.class;
        this.globalSettingValueConverter = new GlobalSettingStringValueConverter();
    }
}
