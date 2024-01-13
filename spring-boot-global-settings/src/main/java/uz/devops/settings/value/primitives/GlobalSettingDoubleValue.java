package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingDoubleValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingDoubleValue extends GlobalSettingValue<Double> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingDoubleValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = Double.class;
        this.globalSettingValueConverter = new GlobalSettingDoubleValueConverter();
    }
}
