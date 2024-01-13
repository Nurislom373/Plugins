package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingBooleanValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingBooleanValueConverter() {
    }

    public String serializeValue(Object value) {
        return Boolean.toString((Boolean)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Boolean.parseBoolean(value) : null;
    }
}
