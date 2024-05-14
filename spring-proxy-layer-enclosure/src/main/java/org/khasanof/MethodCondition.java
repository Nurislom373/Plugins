package org.khasanof;

import java.lang.reflect.Method;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 5/14/2024 10:11 AM
 */
public interface MethodCondition {

    /**
     *
     * @param method
     * @param args
     * @return
     */
    boolean condition(Method method, Object[] args);
}
