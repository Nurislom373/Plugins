package uz.devops.settings.converter.non_primitives;

import uz.devops.settings.converter.GlobalSettingTypeValueConverter;

/**
 * @author Nurislom
 * @see uz.devops.settings.converter.non_primitives
 * @since 11/23/2023 5:29 PM
 */
public class GlobalSettingEnumValueConverter implements GlobalSettingTypeValueConverter {

    private Class<? extends Enum> type;

    @Override
    @SuppressWarnings({"rawtypes"})
    public String serializeValue(Object value) throws Exception {
        if (value instanceof Enum) {
            Enum eValue = (Enum) value;
            return eValue.name();
        }
        return null;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public Object deserializeValue(String value) throws Exception {
        return Enum.valueOf(type, value);
    }

    public GlobalSettingEnumValueConverter type(Class<? extends Enum> type) {
        this.type = type;
        return this;
    }

}
