package org.khasanof.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author Nurislom
 * @see org.khasanof.utils
 * @since 4/24/2024 3:51 PM
 */
public abstract class GenericUtils {

    /**
     *
     * @param type
     * @return
     */
    public static Class<?> getClassGenericType(Class<?> type) {
        Type genericClass = type.getGenericSuperclass();
        if (Objects.isNull(genericClass)) {
            Type[] genericInterfaces = type.getGenericInterfaces();
            if (genericInterfaces.length == 0) {
                return null;
            }
            genericClass = genericInterfaces[0];
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericClass;
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    /**
     *
     * @param field
     * @return
     */
    public static Class<?> getFieldGenericType(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }
}
