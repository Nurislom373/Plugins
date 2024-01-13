package uz.devops.settings;

import com.google.common.primitives.Primitives;
import org.junit.jupiter.api.Test;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.service.configuration.value.ConfigurationValueStrategy;
import uz.devops.settings.service.configuration.value.DefaultConfigurationValueStrategy;
import uz.devops.settings.service.dto.configuration.Value;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nurislom
 * @see uz.devops.settings
 * @since 12/22/2023 10:48 AM
 */
public class ConfigurationValueStrategyTest {

    private final ConfigurationValueStrategy valueStrategy = new DefaultConfigurationValueStrategy(null);

    @Test
    void testCreateDefaultValueShouldSuccess() {
        Value value = valueStrategy.create(settingFields(), String.class);
        assertAll(() -> {
            assertEquals(value.getValue(), false);
            assertEquals(value.getValue().getClass(), Boolean.class);
        });
    }

    GlobalSettingFields settingFields() {
        GlobalSettingFields globalSettingFields = new GlobalSettingFields();
        globalSettingFields.setId(1L);
        globalSettingFields.setFieldValue("false");
        globalSettingFields.setFieldType("BOOLEAN");
        globalSettingFields.setInputType(InputType.CHECKBOX);
        globalSettingFields.setFieldClassType("boolean");
        return globalSettingFields;
    }

}
