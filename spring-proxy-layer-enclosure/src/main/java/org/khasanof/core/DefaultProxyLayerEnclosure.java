package org.khasanof.core;

import org.khasanof.MethodCondition;
import org.khasanof.ProxyAction;
import org.khasanof.ProxyLayerEnclosure;
import org.khasanof.core.factories.action.ProxyActionFactory;
import org.khasanof.core.factories.condition.RegexMethodConditionFactory;
import org.khasanof.core.factories.object.ProxyObjectFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Nurislom
 * @see org.khasanof.core
 * @since 5/14/2024 10:04 AM
 */
@Component
@SuppressWarnings({"unchecked"})
public class DefaultProxyLayerEnclosure implements ProxyLayerEnclosure {

    private final ProxyActionFactory proxyActionFactory;
    private final ProxyObjectFactory proxyObjectFactory;
    private final RegexMethodConditionFactory regexMethodConditionFactory;

    public DefaultProxyLayerEnclosure(ProxyActionFactory proxyActionFactory,
                                      ProxyObjectFactory proxyObjectFactory,
                                      RegexMethodConditionFactory regexMethodConditionFactory) {

        this.proxyActionFactory = proxyActionFactory;
        this.proxyObjectFactory = proxyObjectFactory;
        this.regexMethodConditionFactory = regexMethodConditionFactory;
    }

    /**
     *
     * @param supplier
     * @param regexForMethodName
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    @Override
    public <T> T addProxyLayer(Supplier<T> supplier, String regexForMethodName, Consumer<T> objectUpdateAction) {
        return addProxyLayer(supplier.get(), regexForMethodName, objectUpdateAction);
    }

    /**
     *
     * @param supplier
     * @param condition
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    @Override
    public <T> T addProxyLayer(Supplier<T> supplier, MethodCondition condition, Consumer<T> objectUpdateAction) {
        return addProxyLayer(supplier.get(), condition, objectUpdateAction);
    }

    /**
     *
     * @param object
     * @param regexForMethodName
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    @Override
    public <T> T addProxyLayer(T object, String regexForMethodName, Consumer<T> objectUpdateAction) {
        MethodCondition condition = regexMethodConditionFactory.create(regexForMethodName);
        return addProxyLayer(object, condition, objectUpdateAction);
    }

    /**
     *
     * @param object
     * @param condition
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    @Override
    public <T> T addProxyLayer(T object, MethodCondition condition, Consumer<T> objectUpdateAction) {
        ProxyAction proxyAction = proxyActionFactory.create(condition, (Consumer<Object>) objectUpdateAction);
        return proxyObjectFactory.create(object, Set.of(proxyAction));
    }
}
