package org.khasanof.core.factories.condition;

import org.khasanof.MethodCondition;

/**
 * @author Nurislom
 * @see org.khasanof.core.factories
 * @since 5/14/2024 10:48 AM
 */
public interface RegexMethodConditionFactory {

    /**
     *
     * @param regex
     * @return
     */
    MethodCondition create(String regex);
}
