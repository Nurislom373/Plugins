package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingFloatValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingFloatValue extends GlobalSettingValue<Float> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingFloatValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = Float.class;
        this.globalSettingValueConverter = new GlobalSettingFloatValueConverter();
    }
}
