package org.khasanof.processor.type;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;

import java.lang.reflect.ParameterizedType;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/23/2024 6:16 PM
 */
public interface FieldTypeStrategy<T extends ObjectField> {

    /**
     *
     * @param field
     * @return
     */
    boolean condition(Property field);

    /**
     *
     * @return
     */
    String fieldType();

    /**
     *
     * @return
     */
    default void action(T objectField, Property property) {
        objectField.setFieldType(fieldType());
    }

    default Class<?> getType() {
        return (Class<?>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
