package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

public class GlobalSettingByteValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingByteValueConverter() {
    }

    public String serializeValue(Object value) {
        return Byte.toString((Byte)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Byte.parseByte(value) : null;
    }
}
