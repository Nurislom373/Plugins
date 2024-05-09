package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/23/2024 6:45 PM
 */
@Component
public class StringFieldTypeStrategy implements FieldTypeStrategy<ObjectField> {

    @Override
    public boolean condition(Property field) {
        return field.getFieldType().equals(String.class);
    }

    @Override
    public String fieldType() {
        return GlobalConstants.TEXT;
    }
}
