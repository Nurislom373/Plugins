package org.khasanof.interceptor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import net.minidev.json.JSONObject;
import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author Nurislom
 * @see org.khasanof.interceptor
 * @since 4/24/2024 1:46 PM
 */
@Component
public class RequireObjectFieldInterceptor implements ObjectFieldInterceptor {

    @Override
    public void intercept(ObjectField field, Property property) {
        if (isRequireField(property.getField())) {
            JSONObject options = field.getOptions();
            options.appendField(GlobalConstants.REQUIRED, true);
        }
    }

    private boolean isRequireField(Field field) {
        return field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(NotBlank.class)
                || field.isAnnotationPresent(NotEmpty.class);
    }
}
