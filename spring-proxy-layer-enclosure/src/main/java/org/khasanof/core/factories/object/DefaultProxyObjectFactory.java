package org.khasanof.core.factories.object;

import org.khasanof.ProxyAction;
import org.khasanof.core.ProxyLayerMethodInterceptor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Nurislom
 * @see org.khasanof.core.factories
 * @since 5/14/2024 10:44 AM
 */
@Component
@SuppressWarnings({"unchecked"})
public class DefaultProxyObjectFactory implements ProxyObjectFactory {

    @Override
    public <T> T create(T object, Set<ProxyAction> proxyActions) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(new ProxyLayerMethodInterceptor(object, proxyActions));
        return (T) enhancer.create();
    }
}
