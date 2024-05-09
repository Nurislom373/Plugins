package org.khasanof.reader;

import org.khasanof.field.Property;

import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.reader
 * @since 4/23/2024 6:06 PM
 */
public interface DataFieldsReader {

    /**
     *
     * @param type
     * @return
     */
    List<Property> getDataFields(Class<?> type);
}
