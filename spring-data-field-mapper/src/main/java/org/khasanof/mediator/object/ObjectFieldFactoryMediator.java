package org.khasanof.mediator.object;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;

import java.util.Optional;

/**
 * @author Nurislom
 * @see org.khasanof.mediator
 * @since 4/23/2024 7:20 PM
 */
public interface ObjectFieldFactoryMediator {

    /**
     *
     * @param property
     * @param strategyType
     * @return
     */
    Optional<ObjectField> create(Property property, Class<?> strategyType);
}
