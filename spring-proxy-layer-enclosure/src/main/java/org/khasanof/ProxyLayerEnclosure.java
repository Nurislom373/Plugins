package org.khasanof;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 5/14/2024 10:01 AM
 */
public interface ProxyLayerEnclosure {

    /**
     *
     * @param supplier
     * @param regexForMethodName
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    <T> T addProxyLayer(Supplier<T> supplier, String regexForMethodName, Consumer<T> objectUpdateAction);

    /**
     *
     * @param supplier
     * @param condition
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    <T> T addProxyLayer(Supplier<T> supplier, MethodCondition condition, Consumer<T> objectUpdateAction);

    /**
     *
     * @param object
     * @param regexForMethodName
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    <T> T addProxyLayer(T object, String regexForMethodName, Consumer<T> objectUpdateAction);

    /**
     *
     * @param object
     * @param condition
     * @param objectUpdateAction
     * @return
     * @param <T>
     */
    <T> T addProxyLayer(T object, MethodCondition condition, Consumer<T> objectUpdateAction);
}
