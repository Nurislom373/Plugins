package org.khasanof;

import java.util.function.Consumer;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 5/14/2024 10:14 AM
 */
public interface ProxyAction {

    /**
     *
     * @return
     */
    MethodCondition methodCondition();

    /**
     *
     * @return
     */
    Consumer<Object> methodAction();
}
