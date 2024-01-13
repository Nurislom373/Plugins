package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingLongValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingLongValueConverter() {
    }

    public String serializeValue(Object value) {
        return Long.toString((Long)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Long.parseLong(value) : null;
    }
}
