package org.khasanof.processor.type.composite;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.field.Property;
import org.khasanof.processor.type.FieldTypeStrategy;
import org.khasanof.registry.FieldTypeRegistry;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nurislom
 * @see org.khasanof.processor.type
 * @since 4/24/2024 2:45 PM
 */
@Component
@SuppressWarnings({"rawtypes"})
public class DefaultCompositeFieldTypeStrategy implements CompositeFieldTypeStrategy {

    private final FieldTypeRegistry registry;
    private final Set<FieldTypeStrategy> strategies = new HashSet<>();
    private boolean isLoadStrategies = false;

    public DefaultCompositeFieldTypeStrategy(@Lazy FieldTypeRegistry registry) {
        this.registry = registry;
    }

    @Override
    public String fieldType(Property property) {
        loadStrategies();
        for (FieldTypeStrategy strategy : strategies) {
            if (strategy.condition(property)) {
                return strategy.fieldType();
            }
        }
        return fieldType();
    }

    @Override
    public String fieldType() {
        return GlobalConstants.UNKNOWN;
    }

    private void loadStrategies() {
        if (!isLoadStrategies) {
            isLoadStrategies = true;
            internalLoadStrategies();
        }
    }

    private void internalLoadStrategies() {
        registry.getAll().forEach(this::checkStrategy);
    }

    private void checkStrategy(FieldTypeStrategy strategy) {
        if (!strategy.getClass().equals(this.getClass())) {
            strategies.add(strategy);
        }
    }
}
