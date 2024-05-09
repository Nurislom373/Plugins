package org.khasanof.field;

import java.lang.reflect.Field;

/**
 * @author Nurislom
 * @see org.khasanof.field
 * @since 4/24/2024 3:55 PM
 */
public record DefaultProperty(Field field) implements Property {

    @Override
    public String getFieldName() {
        return this.field.getName();
    }

    @Override
    public Class<?> getFieldType() {
        return this.field.getType();
    }

    @Override
    public Field getField() {
        return this.field;
    }
}
