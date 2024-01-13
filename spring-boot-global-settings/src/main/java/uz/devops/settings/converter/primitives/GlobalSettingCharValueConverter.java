package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingCharValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingCharValueConverter() {
    }

    public String serializeValue(Object value) {
        return Character.toString((Character)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? value.charAt(0) : null;
    }
}
