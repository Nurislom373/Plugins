package org.khasanof.factories.property;

import org.khasanof.field.Property;
import org.khasanof.field.TypeProperty;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.factories.property
 * @since 4/24/2024 3:57 PM
 */
@Component
public class TypePropertyFactory implements PropertyFactory {

    @Override
    public Property create(Class<?> type, String fieldName) {
        TypeProperty typeProperty = new TypeProperty();
        typeProperty.setFieldType(type);
        typeProperty.setFieldName(fieldName);
        return typeProperty;
    }
}
