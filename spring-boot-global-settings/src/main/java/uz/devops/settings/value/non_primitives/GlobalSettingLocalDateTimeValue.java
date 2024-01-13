package uz.devops.settings.value.non_primitives;

import uz.devops.settings.converter.non_primitives.GlobalSettingLocalDateTimeConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

import java.time.LocalDateTime;

/**
 * @author Nurislom
 * @see uz.devops.settings.value.non_primitives
 * @since 11/18/2023 6:15 PM
 */
public class GlobalSettingLocalDateTimeValue extends GlobalSettingValue<LocalDateTime> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "DATETIME";

    public GlobalSettingLocalDateTimeValue() {
        this.inputType = InputType.DATE;
        this.fieldType = "DATETIME";
        this.settingValueClass = LocalDateTime.class;
        this.globalSettingValueConverter = new GlobalSettingLocalDateTimeConverter();
    }
}
