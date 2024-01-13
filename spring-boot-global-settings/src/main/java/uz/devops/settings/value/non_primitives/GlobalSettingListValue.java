package uz.devops.settings.value.non_primitives;

import uz.devops.settings.converter.non_primitives.GlobalSettingListValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.value.non_primitives
 * @since 11/18/2023 4:39 PM
 */
public class GlobalSettingListValue extends GlobalSettingValue<List> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "JSON";

    public GlobalSettingListValue() {
        this.inputType = InputType.JSONAREA;
        this.fieldType = "JSON";
        this.settingValueClass = List.class;
        this.globalSettingValueConverter = new GlobalSettingListValueConverter();
    }

}
