package org.khasanof.factories.field;

import org.khasanof.field.Property;
import org.khasanof.field.data.ArrayField;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.factories
 * @since 4/24/2024 11:57 AM
 */
@Component
public class ArrayObjectFieldFactory implements ObjectFieldFactory<ArrayField> {

    @Override
    public ArrayField create(Property property) {
        ArrayField arrayField = new ArrayField();
        arrayField.setFieldName(property.getFieldName());
        return arrayField;
    }
}
