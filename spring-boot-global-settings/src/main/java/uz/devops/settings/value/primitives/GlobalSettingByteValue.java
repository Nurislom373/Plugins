package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingByteValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

public class GlobalSettingByteValue extends GlobalSettingValue<Byte> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingByteValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = Byte.class;
        this.globalSettingValueConverter = new GlobalSettingByteValueConverter();
    }
}
