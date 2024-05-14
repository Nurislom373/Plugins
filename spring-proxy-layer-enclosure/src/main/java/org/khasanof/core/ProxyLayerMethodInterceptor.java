package org.khasanof.core;

import org.khasanof.MethodCondition;
import org.khasanof.ProxyAction;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Nurislom
 * @see org.khasanof.core
 * @since 5/14/2024 10:08 AM
 */
public class ProxyLayerMethodInterceptor implements MethodInterceptor {

    private final Object realInstance;
    private final Set<ProxyAction> proxyActions;

    public ProxyLayerMethodInterceptor(Object realInstance, Set<ProxyAction> proxyActions) {
        this.realInstance = realInstance;
        this.proxyActions = proxyActions;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object invokeResult = method.invoke(realInstance, args);
        iterateProxyActions(method, args);
        return invokeResult;
    }

    private void iterateProxyActions(Method method, Object[] args) {
        proxyActions.forEach(proxyAction -> {
            if (methodCondition(method, args, proxyAction)) {
                executeAction(proxyAction);
            }
        });
    }

    private boolean methodCondition(Method method, Object[] args, ProxyAction proxyAction) {
        MethodCondition methodCondition = proxyAction.methodCondition();
        if (Objects.nonNull(methodCondition)) {
            return methodCondition.condition(method, args);
        }
        return false;
    }

    private void executeAction(ProxyAction proxyAction) {
        Consumer<Object> methodAction = proxyAction.methodAction();
        if (Objects.nonNull(methodAction)) {
            methodAction.accept(realInstance);
            return;
        }
        throw new RuntimeException("Method Action is null!");
    }
}
