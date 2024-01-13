package uz.devops.settings.service.configuration.value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.enumuration.FieldType;
import uz.devops.settings.service.configuration.ConfigurationServiceUtils;
import uz.devops.settings.service.dto.configuration.Value;
import uz.devops.settings.service.dto.configuration.value.TextSelectedValue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration.value
 * @since 11/23/2023 6:32 PM
 */
@Slf4j
@Service
public class EnumConfigurationValueStrategy implements ConfigurationValueStrategy {

    private static final Value EMPTY_VALUE = new TextSelectedValue();
    private final ApplicationContext applicationContext;

    public EnumConfigurationValueStrategy(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Value create(GlobalSettingFields fields, Class<?> configurationClass) {
        return createValue(fields);
    }

    @Override
    public FieldType getKey() {
        return FieldType.ENUM;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Value createValue(GlobalSettingFields fields) {
        Class<?> enumClass = springClassLoader(fields.getFieldClassType(), applicationContext);
        if (Objects.nonNull(enumClass) && Enum.class.isAssignableFrom(enumClass)) {
            return createValueInstance(fields.getFieldValue(), getEnumValues((Class<? extends Enum>) enumClass));
        }
        return EMPTY_VALUE;
    }

    private static List<String> getEnumValues(Class<? extends Enum> instance) {
        return Arrays.stream(instance.getEnumConstants()).map(Enum::name)
                .collect(Collectors.toList());
    }

    private Value createValueInstance(String value, List<String> options) {
        return new TextSelectedValue(value, options);
    }
}
