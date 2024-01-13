package uz.devops.settings.service.context.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import uz.devops.settings.util.BaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.converter
 * @since 11/25/2023 11:59 PM
 */
public abstract class AbstractObjectConverter implements ObjectConverter {

    protected final ObjectMapper objectMapper = BaseUtils.OBJECT_MAPPER;
    protected final ApplicationContext applicationContext;

    public AbstractObjectConverter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    protected final Type[] getActualTypeArguments(Field settingField) {
        return ((ParameterizedType) settingField.getGenericType()).getActualTypeArguments();
    }

    protected final List<Class<?>> getGenericsTypes(Type[] actualTypeArguments, ApplicationContext applicationContext) {
        ClassLoader springClassloader = applicationContext.getClassLoader();
        if (Objects.nonNull(springClassloader)) {
            if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length != 0) {
                return Arrays.stream(actualTypeArguments)
                        .map(type -> {
                            Class<?> convertClass = (Class<?>) type;
                            if (!Objects.equals(convertClass.getClassLoader(), springClassloader)) {
                                Class<?> convertSpringLoader = springClassLoader(convertClass.getName(), applicationContext);
                                if (Objects.nonNull(convertSpringLoader)) {
                                    convertClass = convertSpringLoader;
                                }
                            }
                            return convertClass;
                        })
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    protected final List<Class<?>> getGenericsTypes(Type[] actualTypeArguments) {
        if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length != 0) {
            return Arrays.stream(actualTypeArguments)
                    .map(type -> (Class<?>) type).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
