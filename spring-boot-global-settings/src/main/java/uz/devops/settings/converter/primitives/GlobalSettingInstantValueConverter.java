package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class GlobalSettingInstantValueConverter implements GlobalSettingValueConverter {
    private DateTimeFormatter fmt;

    public GlobalSettingInstantValueConverter() {
        this.fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
    }

    public String serializeValue(Object value) {
        return this.fmt.format((Instant)value);
    }

    public Object deserializeValue(String value) {
        return value != null && value.length() != 0 ? Instant.from(this.fmt.parse(value)) : null;
    }
}
