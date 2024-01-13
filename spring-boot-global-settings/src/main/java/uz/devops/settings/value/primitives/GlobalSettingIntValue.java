package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingIntValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingIntValue extends GlobalSettingValue<Integer> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingIntValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = Integer.class;
        this.globalSettingValueConverter = new GlobalSettingIntValueConverter();
    }
}
