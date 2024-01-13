package uz.devops.settings.value.non_primitives;

import uz.devops.settings.converter.non_primitives.GlobalSettingUnknownValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

/**
 * @author Nurislom
 * @see uz.devops.settings.value.non_primitives
 * @since 11/20/2023 5:47 PM
 */
public class GlobalSettingUnknownValue extends GlobalSettingValue<Object> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "JSON";

    public GlobalSettingUnknownValue() {
        this.inputType = InputType.JSONAREA;
        this.fieldType = "JSON";
        this.settingValueClass = Object.class;
        this.globalSettingValueConverter = new GlobalSettingUnknownValueConverter();
    }

}
