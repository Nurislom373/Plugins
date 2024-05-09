package org.khasanof.factories.field;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;

import java.lang.reflect.ParameterizedType;

/**
 * @author Nurislom
 * @see org.khasanof.factories
 * @since 4/23/2024 7:01 PM
 */
public interface ObjectFieldFactory<T extends ObjectField> {

    T create(Property property);

    default Class<?> getType() {
        return (Class<?>) ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }
}
