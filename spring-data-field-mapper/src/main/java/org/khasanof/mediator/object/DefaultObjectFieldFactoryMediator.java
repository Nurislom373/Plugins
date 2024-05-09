package org.khasanof.mediator.object;

import org.khasanof.factories.field.ObjectFieldFactory;
import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nurislom
 * @see org.khasanof.mediator
 * @since 4/23/2024 7:21 PM
 */
@Component
@SuppressWarnings({"rawtypes"})
public class DefaultObjectFieldFactoryMediator implements ObjectFieldFactoryMediator, InitializingBean {

    private final ApplicationContext applicationContext;
    private final Map<Class<?>, ObjectFieldFactory> factoryMap = new HashMap<>();

    public DefaultObjectFieldFactoryMediator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Optional<ObjectField> create(Property property, Class<?> strategyType) {
        return Optional.ofNullable(factoryMap.get(strategyType))
                .map(objectFieldFactory -> objectFieldFactory.create(property));
    }

    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(ObjectFieldFactory.class)
                .values()
                .forEach(objectFieldFactory -> this.factoryMap.put(objectFieldFactory.getType(), objectFieldFactory));
    }
}
