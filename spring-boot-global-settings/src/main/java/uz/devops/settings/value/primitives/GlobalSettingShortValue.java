package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingShortValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingShortValue extends GlobalSettingValue<Short> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingShortValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = Short.class;
        this.globalSettingValueConverter = new GlobalSettingShortValueConverter();
    }
}
