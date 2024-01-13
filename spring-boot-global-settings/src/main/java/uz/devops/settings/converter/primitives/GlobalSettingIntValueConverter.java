package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingIntValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingIntValueConverter() {
    }

    public String serializeValue(Object value) {
        return Integer.toString((Integer)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Integer.parseInt(value) : null;
    }
}
