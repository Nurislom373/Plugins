package org.khasanof.interceptor;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;

/**
 * @author Nurislom
 * @see org.khasanof.interceptor
 * @since 4/24/2024 1:12 PM
 */
public interface ObjectFieldInterceptor {

    /**
     *
     * @param field
     */
    void intercept(ObjectField field, Property property);
}
