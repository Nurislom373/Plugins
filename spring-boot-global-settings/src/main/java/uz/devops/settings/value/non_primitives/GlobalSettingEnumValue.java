package uz.devops.settings.value.non_primitives;

import uz.devops.settings.converter.non_primitives.GlobalSettingEnumValueConverter;
import uz.devops.settings.converter.non_primitives.GlobalSettingListValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.value.GlobalSettingValue;

/**
 * @author Nurislom
 * @see uz.devops.settings.value.non_primitives
 * @since 11/23/2023 5:40 PM
 */
public class GlobalSettingEnumValue extends GlobalSettingValue<Enum> {

    public GlobalSettingEnumValue() {
        this.inputType = InputType.SELECT;
        this.fieldType = "ENUM";
        this.settingValueClass = Enum.class;
        this.globalSettingValueConverter = new GlobalSettingEnumValueConverter();
    }

}
