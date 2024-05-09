package org.khasanof.mediator.interceptor;

import org.khasanof.field.Property;
import org.khasanof.field.data.ObjectField;
import org.khasanof.interceptor.ObjectFieldInterceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nurislom
 * @see org.khasanof.mediator.interceptor
 * @since 4/24/2024 1:55 PM
 */
@Component
public class DefaultObjectFieldInterceptorMediator implements ObjectFieldInterceptorMediator, InitializingBean {

    private final ApplicationContext applicationContext;
    private final Set<ObjectFieldInterceptor> interceptors = new HashSet<>();

    public DefaultObjectFieldInterceptorMediator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void intercept(ObjectField field, Property property) {
        this.interceptors.forEach(objectFieldInterceptor -> objectFieldInterceptor.intercept(field, property));
    }

    @Override
    public void afterPropertiesSet() {
        interceptors.addAll(getObjectFieldInterceptors());
    }

    private Collection<ObjectFieldInterceptor> getObjectFieldInterceptors() {
        return applicationContext.getBeansOfType(ObjectFieldInterceptor.class)
                .values();
    }
}
