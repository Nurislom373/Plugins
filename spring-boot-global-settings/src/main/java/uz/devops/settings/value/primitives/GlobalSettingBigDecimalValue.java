package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingBigDecimalValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

import java.math.BigDecimal;

public class GlobalSettingBigDecimalValue extends GlobalSettingValue<BigDecimal> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "NUMBER";

    public GlobalSettingBigDecimalValue() {
        this.inputType = InputType.INPUT_NUMBER;
        this.fieldType = "NUMBER";
        this.settingValueClass = BigDecimal.class;
        this.globalSettingValueConverter = new GlobalSettingBigDecimalValueConverter();
    }
}
