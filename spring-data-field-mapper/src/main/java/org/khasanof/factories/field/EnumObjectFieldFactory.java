package org.khasanof.factories.field;

import org.khasanof.field.Property;
import org.khasanof.field.data.EnumField;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.factories
 * @since 4/24/2024 12:00 PM
 */
@Component
@SuppressWarnings({"rawtypes"})
public class EnumObjectFieldFactory implements ObjectFieldFactory<EnumField> {

    @Override
    public EnumField create(Property property) {
        EnumField enumField = new EnumField();
        enumField.setFieldName(property.getFieldName());
        enumField.setValues(getEnumConstants(property));
        return enumField;
    }

    private List<String> getEnumConstants(Property property) {
        return Arrays.stream(property.getFieldType().getEnumConstants())
                .map(wildCard -> ((Enum) wildCard).name())
                .toList();
    }
}
