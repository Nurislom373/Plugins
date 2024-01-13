package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingShortValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingShortValueConverter() {
    }

    public String serializeValue(Object value) {
        return Short.toString((Short)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Short.parseShort(value) : null;
    }
}
