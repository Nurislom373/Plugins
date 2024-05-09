package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 12:14 PM
 */
@Component
public class NumberFieldTypeStrategy implements FieldTypeStrategy<ObjectField> {

    @Override
    public boolean condition(Property property) {
        return property.getFieldType().equals(Number.class) ||
                Number.class.isAssignableFrom(property.getFieldType()) ||
                primitiveNumbers(property);
    }

    private boolean primitiveNumbers(Property property) {
        return int.class.equals(property.getFieldType()) || long.class.equals(property.getFieldType())
                || short.class.equals(property.getFieldType()) || byte.class.equals(property.getFieldType())
                || double.class.equals(property.getFieldType()) || float.class.equals(property.getFieldType());
    }

    @Override
    public String fieldType() {
        return GlobalConstants.NUMBER;
    }
}
