package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingDoubleValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingDoubleValueConverter() {
    }

    public String serializeValue(Object value) {
        return Double.toString((Double)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Double.parseDouble(value) : null;
    }
}
