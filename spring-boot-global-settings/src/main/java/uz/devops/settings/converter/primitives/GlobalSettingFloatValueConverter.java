package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingFloatValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingFloatValueConverter() {
    }

    public String serializeValue(Object value) {
        return Float.toString((Float)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Float.parseFloat(value) : null;
    }
}
