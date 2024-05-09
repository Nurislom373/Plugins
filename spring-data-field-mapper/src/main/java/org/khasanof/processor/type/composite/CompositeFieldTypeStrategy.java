package org.khasanof.processor.type.composite;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.khasanof.processor.type.FieldTypeStrategy;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 2:49 PM
 */
public interface CompositeFieldTypeStrategy extends FieldTypeStrategy<ObjectField> {

    @Override
    default boolean condition(Property field) {
        return false; // because is composite
    }

    /**
     *
     * @param property
     * @return
     */
    String fieldType(Property property);
}
