package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 3:29 PM
 */
@Component
public class BooleanFieldTypeStrategy implements FieldTypeStrategy<ObjectField> {

    @Override
    public boolean condition(Property field) {
        return boolean.class.equals(field.getFieldType()) || Boolean.class.equals(field.getFieldType());
    }

    @Override
    public String fieldType() {
        return GlobalConstants.BOOLEAN;
    }
}
