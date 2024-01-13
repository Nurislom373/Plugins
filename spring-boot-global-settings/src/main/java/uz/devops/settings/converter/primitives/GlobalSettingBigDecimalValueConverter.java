package uz.devops.settings.converter.primitives;

import uz.devops.settings.converter.GlobalSettingValueConverter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class GlobalSettingBigDecimalValueConverter implements GlobalSettingValueConverter {
    public GlobalSettingBigDecimalValueConverter() {
    }

    public String serializeValue(Object value) {
        return Boolean.toString((Boolean)value);
    }

    public Object deserializeValue(String value) throws ParseException {
        if (value != null && value.length() != 0) {
            if (value.contains(",")) {
                value = value.replace(",", ".");
            }

            if (value.contains(".")) {
                NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
                Number number = numberFormat.parse(value);
                return BigDecimal.valueOf(number.doubleValue());
            } else {
                return BigDecimal.valueOf(Long.valueOf(value));
            }
        } else {
            return null;
        }
    }
}
