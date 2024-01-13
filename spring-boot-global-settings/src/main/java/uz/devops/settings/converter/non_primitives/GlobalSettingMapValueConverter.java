package uz.devops.settings.converter.non_primitives;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import uz.devops.settings.converter.AbstractGlobalSettingValueConverter;

import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.converter.primitives
 * @since 11/18/2023 3:43 PM
 */
public class GlobalSettingMapValueConverter extends AbstractGlobalSettingValueConverter {

    @Override
    public String serializeValue(Object value) throws Exception {
        return ifCheckFail(() -> check(value), () -> serializeJson(value));
    }

    @Override
    public Object deserializeValue(String value) throws Exception {
        return ifCheckFail(() -> check(value), () -> deserializeJson(value));
    }

    private Object deserializeJson(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, new TypeReference<Map>() {});
    }

    private String serializeJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

}
