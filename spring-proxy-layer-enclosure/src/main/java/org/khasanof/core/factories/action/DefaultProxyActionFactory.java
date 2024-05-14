package org.khasanof.core.factories.action;

import org.khasanof.MethodCondition;
import org.khasanof.ProxyAction;
import org.khasanof.core.DefaultProxyAction;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author Nurislom
 * @see org.khasanof.core.factories
 * @since 5/14/2024 10:27 AM
 */
@Component
public class DefaultProxyActionFactory implements ProxyActionFactory {

    /**
     *
     * @param condition
     * @param action
     * @return
     */
    @Override
    public ProxyAction create(MethodCondition condition, Consumer<Object> action) {
        return new DefaultProxyAction(condition, action);
    }
}
