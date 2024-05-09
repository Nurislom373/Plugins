package org.khasanof.reader;

import org.khasanof.field.DefaultProperty;
import org.khasanof.field.Property;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.reader
 * @since 4/23/2024 6:07 PM
 */
@Component
public class DefaultDataFieldsReader implements DataFieldsReader {

    @Override
    public List<Property> getDataFields(Class<?> type) {
        return iterateFieldsAndConvert(Arrays.asList(type.getDeclaredFields()));
    }

    private List<Property> iterateFieldsAndConvert(List<Field> fields) {
        return fields.stream()
                .map(this::createDataField)
                .toList();
    }

    private Property createDataField(Field field) {
        return new DefaultProperty(field);
    }
}
