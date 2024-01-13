package uz.devops.settings.value.primitives;

import uz.devops.settings.converter.primitives.GlobalSettingInstantValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

import java.time.Instant;

public class GlobalSettingInstantValue extends GlobalSettingValue<Instant> {
    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "DATETIME";

    public GlobalSettingInstantValue() {
        this.inputType = InputType.DATE;
        this.fieldType = "DATETIME";
        this.settingValueClass = Instant.class;
        this.globalSettingValueConverter = new GlobalSettingInstantValueConverter();
    }
}
