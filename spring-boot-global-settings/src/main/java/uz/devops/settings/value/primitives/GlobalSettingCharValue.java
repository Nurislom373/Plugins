package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingCharValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingCharValue extends GlobalSettingValue<Character> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "TEXT";

    public GlobalSettingCharValue() {
        this.inputType = InputType.INPUT_TEXT;
        this.fieldType = "TEXT";
        this.settingValueClass = Character.class;
        this.globalSettingValueConverter = new GlobalSettingCharValueConverter();
    }
}
