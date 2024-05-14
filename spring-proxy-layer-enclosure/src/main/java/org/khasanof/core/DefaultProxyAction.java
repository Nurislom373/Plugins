package org.khasanof.core;

import lombok.*;
import org.khasanof.MethodCondition;
import org.khasanof.ProxyAction;

import java.util.function.Consumer;

/**
 * @author Nurislom
 * @see org.khasanof.core
 * @since 5/14/2024 10:41 AM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefaultProxyAction implements ProxyAction {

    private MethodCondition condition;
    private Consumer<Object> action;

    @Override
    public MethodCondition methodCondition() {
        return this.condition;
    }

    @Override
    public Consumer<Object> methodAction() {
        return this.action;
    }
}
