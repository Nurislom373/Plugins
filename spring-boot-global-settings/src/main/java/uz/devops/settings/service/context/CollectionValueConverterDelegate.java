package uz.devops.settings.service.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.service.context.converter.ObjectConverter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context
 * @since 11/25/2023 10:56 PM
 */
@Slf4j
@Service
public class CollectionValueConverterDelegate implements InitializingBean {

    private final Map<Class<?>, ObjectConverter> converterMap = new HashMap<>();
    private final ApplicationContext applicationContext;

    public CollectionValueConverterDelegate(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object linkedMapConvertToGenericCollection(Object value, Field settingField) {
        return converterMap.get(findMatchKey(value.getClass())).convert(value, settingField);
    }

    private Class<?> findMatchKey(Class<?> valueType) {
        if (!converterMap.containsKey(valueType)) {
            return converterMap.keySet()
                    .stream().filter(key -> key.isAssignableFrom(valueType))
                    .findFirst().orElseThrow(() -> new RuntimeException("Match key not found!"));
        }
        return valueType;
    }

    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(ObjectConverter.class)
                .forEach((beanName, bean) -> converterMap.put(bean.getType(), bean));
    }

}
