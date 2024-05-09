package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type.composite
 * @since 4/24/2024 3:18 PM
 */
@Component
public class DateFieldTypeStrategy implements FieldTypeStrategy<ObjectField> {

    @Override
    public boolean condition(Property field) {
        return field.getFieldType().equals(Date.class) || field.getFieldType().equals(LocalDate.class);
    }

    @Override
    public String fieldType() {
        return GlobalConstants.DATE;
    }
}
