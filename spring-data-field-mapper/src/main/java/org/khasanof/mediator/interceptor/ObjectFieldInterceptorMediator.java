package org.khasanof.mediator.interceptor;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;

/**
 * @author Nurislom
 * @see org.khasanof.mediator.interceptor
 * @since 4/24/2024 1:15 PM
 */
public interface ObjectFieldInterceptorMediator {

    /**
     *
     * @param field
     */
    void intercept(ObjectField field, Property property);
}
