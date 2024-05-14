package org.khasanof.core.factories.action;

import org.khasanof.MethodCondition;
import org.khasanof.ProxyAction;

import java.util.function.Consumer;

/**
 * @author Nurislom
 * @see org.khasanof.core.factories
 * @since 5/14/2024 10:26 AM
 */
public interface ProxyActionFactory {

    /**
     *
     * @param condition
     * @param action
     * @return
     */
    ProxyAction create(MethodCondition condition, Consumer<Object> action);
}
