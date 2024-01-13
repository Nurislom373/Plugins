package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingStringValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingStringValueConverter() {
    }

    public String serializeValue(Object value) {
        return (String)value;
    }

    public Object deserializeValue(String value) {
        return value;
    }
}
