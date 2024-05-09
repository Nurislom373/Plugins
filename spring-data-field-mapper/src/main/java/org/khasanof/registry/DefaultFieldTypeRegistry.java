package org.khasanof.registry;

import org.khasanof.constants.GlobalConstants;
import org.khasanof.processor.type.FieldTypeStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nurislom
 * @see org.khasanof.registry
 * @since 4/23/2024 6:42 PM
 */
@Component
@SuppressWarnings({"rawtypes"})
public class DefaultFieldTypeRegistry implements FieldTypeRegistry, InitializingBean {

    private final ApplicationContext applicationContext;
    private final Set<FieldTypeStrategy> strategies = new HashSet<>();

    public DefaultFieldTypeRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Set<FieldTypeStrategy> getAll() {
        return this.strategies;
    }

    @Override
    public void add(FieldTypeStrategy strategy) {
        if (Objects.nonNull(strategy)) {
            this.strategies.add(strategy);
        }
    }

    @Override
    public void addAll(Collection<FieldTypeStrategy> collection) {
        if (Objects.nonNull(collection) && !collection.isEmpty()) {
            this.strategies.addAll(collection);
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.addAll(getFieldTypeStrategies());
    }

    private Set<FieldTypeStrategy> getFieldTypeStrategies() {
        return applicationContext.getBeansOfType(FieldTypeStrategy.class)
                .values().stream()
                .filter(strategy -> !Objects.equals(strategy.fieldType(), GlobalConstants.UNKNOWN))
                .collect(Collectors.toSet());
    }
}
