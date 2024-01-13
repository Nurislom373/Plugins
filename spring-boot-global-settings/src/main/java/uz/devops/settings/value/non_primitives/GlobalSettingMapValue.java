package uz.devops.settings.value.non_primitives;

import uz.devops.settings.converter.non_primitives.GlobalSettingMapValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.value.non_primitives
 * @since 11/18/2023 4:38 PM
 */
public class GlobalSettingMapValue extends GlobalSettingValue<Map> {

    public final String INPUT_TYPE = "INPUT";
    public final String FIELD_TYPE = "JSON";

    public GlobalSettingMapValue() {
        this.inputType = InputType.JSONAREA;
        this.fieldType = "JSON";
        this.settingValueClass = Map.class;
        this.globalSettingValueConverter = new GlobalSettingMapValueConverter();
    }

}
