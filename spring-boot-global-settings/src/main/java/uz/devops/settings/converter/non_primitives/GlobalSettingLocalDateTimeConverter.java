package uz.devops.settings.converter.non_primitives;

import uz.devops.settings.converter.AbstractGlobalSettingValueConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Nurislom
 * @see uz.devops.settings.converter.non_primitives
 * @since 11/18/2023 6:12 PM
 */
public class GlobalSettingLocalDateTimeConverter extends AbstractGlobalSettingValueConverter {

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneOffset.UTC);


    @Override
    public String serializeValue(Object value) throws Exception {
        return ifCheckFail(() -> check(value), () -> this.fmt.format((LocalDateTime) value));
    }

    @Override
    public Object deserializeValue(String value) throws Exception {
        return ifCheckFail(() -> check(value), () -> LocalDateTime.from(this.fmt.parse(value)));
    }
}
