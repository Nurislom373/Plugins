package org.khasanof.core.factories.object;

import org.khasanof.ProxyAction;

import java.util.Set;

/**
 * @author Nurislom
 * @see org.khasanof.core.factories
 * @since 5/14/2024 10:43 AM
 */
public interface ProxyObjectFactory {

    /**
     *
     * @param object
     * @param proxyActions
     * @return
     * @param <T>
     */
    <T> T create(T object, Set<ProxyAction> proxyActions);
}
