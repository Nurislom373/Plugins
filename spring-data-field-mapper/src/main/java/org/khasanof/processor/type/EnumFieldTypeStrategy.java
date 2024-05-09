package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.EnumField;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 12:38 PM
 */
@Component
public class EnumFieldTypeStrategy implements FieldTypeStrategy<EnumField> {

    @Override
    public boolean condition(Property field) {
        return Enum.class.isAssignableFrom(field.getFieldType());
    }

    @Override
    public String fieldType() {
        return GlobalConstants.ENUM;
    }
}
