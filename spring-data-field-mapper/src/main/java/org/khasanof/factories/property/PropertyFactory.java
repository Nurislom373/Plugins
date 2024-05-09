package org.khasanof.factories.property;

import org.khasanof.field.Property;

/**
 * @author Nurislom
 * @see org.khasanof.factories.property
 * @since 4/24/2024 3:56 PM
 */
public interface PropertyFactory {

    /**
     *
     * @param type
     * @return
     */
    Property create(Class<?> type, String fieldName);
}
