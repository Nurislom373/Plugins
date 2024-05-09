package org.khasanof.factories.field;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.factories
 * @since 4/23/2024 7:13 PM
 */
@Component
public class DefaultObjectFieldFactory implements ObjectFieldFactory<ObjectField> {

    @Override
    public ObjectField create(Property property) {
        ObjectField objectField = new ObjectField();
        objectField.setFieldName(property.getFieldName());
        return objectField;
    }
}
