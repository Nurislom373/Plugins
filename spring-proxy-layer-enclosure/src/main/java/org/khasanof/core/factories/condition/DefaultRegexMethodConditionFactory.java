package org.khasanof.core.factories.condition;

import org.khasanof.MethodCondition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nurislom
 * @see org.khasanof.core.factories
 * @since 5/14/2024 10:48 AM
 */
@Component
public class DefaultRegexMethodConditionFactory implements RegexMethodConditionFactory {

    @Override
    public MethodCondition create(String regex) {
        return (method, args) -> internalCreate(regex, method);
    }

    private boolean internalCreate(String regex, Method method) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(method.getName());
        return matcher.matches();
    }
}
