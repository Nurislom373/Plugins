package uz.devops.settings.value;

import com.google.common.primitives.Primitives;
import lombok.extern.slf4j.Slf4j;
import org.jboss.jandex.PrimitiveType;
import uz.devops.settings.converter.GlobalSettingTypeValueConverter;
import uz.devops.settings.converter.GlobalSettingValueConverter;
import uz.devops.settings.converter.non_primitives.GlobalSettingEnumValueConverter;
import uz.devops.settings.converter.non_primitives.GlobalSettingListValueConverter;
import uz.devops.settings.converter.non_primitives.GlobalSettingMapValueConverter;
import uz.devops.settings.value.non_primitives.*;
import uz.devops.settings.value.primitives.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class GlobalSettingValueFactory {

    private static final Map<Class<?>, Class<?>> values = new HashMap<>();

    public GlobalSettingValueFactory() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static GlobalSettingValue<?> getSettingValue(Class<?> settingType, boolean acceptUnknownType) throws IllegalAccessException, InstantiationException {
        GlobalSettingValue<?> settingValue = getSettingValue(settingType);
        if (acceptUnknownType && Objects.isNull(settingValue)) {
            settingValue = new GlobalSettingUnknownValue();
        }
        if (settingValue instanceof GlobalSettingEnumValue) {
            settingValue.globalSettingValueConverter = new GlobalSettingEnumValueConverter()
                    .type((Class<? extends Enum>) settingType);
        }
        return settingValue;
    }

    public static GlobalSettingValue<?> getSettingValue(Class<?> settingType) throws IllegalAccessException, InstantiationException {
        try {
            if (settingType.isPrimitive()) {
                settingType = Primitives.wrap(settingType);
            }
            return (GlobalSettingValue<?>) ((Class<?>) values.get(findMatchKey(settingType))).newInstance();
        } catch (RuntimeException ex) {
            log.warn("exception thrown : {}", ex.getMessage());
            return null;
        }
    }

    private static Class<?> findMatchKey(Class<?> settingType) {
        if (!values.containsKey(settingType)) {
            return values.keySet().stream()
                    .filter(key -> key.isAssignableFrom(settingType))
                    .findFirst().orElseThrow(() -> new RuntimeException("Match key not found!"));
        }
        return settingType;
    }

    static {
        values.put(BigDecimal.class, GlobalSettingBigDecimalValue.class);
        values.put(Boolean.class, GlobalSettingBooleanValue.class);
        values.put(Byte.class, GlobalSettingByteValue.class);
        values.put(Character.class, GlobalSettingCharValue.class);
        values.put(Double.class, GlobalSettingDoubleValue.class);
        values.put(Float.class, GlobalSettingFloatValue.class);
        values.put(Instant.class, GlobalSettingInstantValue.class);
        values.put(Integer.class, GlobalSettingIntValue.class);
        values.put(Short.class, GlobalSettingShortValue.class);
        values.put(String.class, GlobalSettingStringValue.class);
        values.put(Long.class, GlobalSettingLongValue.class);
        values.put(Map.class, GlobalSettingMapValue.class);
        values.put(List.class, GlobalSettingListValue.class);
        values.put(LocalDateTime.class, GlobalSettingLocalDateTimeValue.class);
        values.put(Enum.class, GlobalSettingEnumValue.class);
    }

}
