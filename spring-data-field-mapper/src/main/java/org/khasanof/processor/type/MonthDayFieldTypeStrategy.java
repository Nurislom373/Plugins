package org.khasanof.processor.type;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

import java.time.MonthDay;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 3:23 PM
 */
@Component
public class MonthDayFieldTypeStrategy implements FieldTypeStrategy<ObjectField> {

    @Override
    public boolean condition(Property field) {
        return field.getFieldType().equals(MonthDay.class);
    }

    @Override
    public String fieldType() {
        return GlobalConstants.MONTH_DAY;
    }
}
