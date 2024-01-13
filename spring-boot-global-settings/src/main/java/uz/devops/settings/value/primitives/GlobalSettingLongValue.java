package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingLongValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingLongValue extends GlobalSettingValue<Long> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingLongValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = Long.class;
        this.globalSettingValueConverter = new GlobalSettingLongValueConverter();
    }
}
