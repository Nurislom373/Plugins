package org.khasanof.field;

import java.lang.reflect.Field;

/**
 * @author Nurislom
 * @see org.khasanof.field
 * @since 4/23/2024 6:04 PM
 */
public interface Property {

    String getFieldName();

    Class<?> getFieldType();

    Field getField();
}
