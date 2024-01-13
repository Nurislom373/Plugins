package uz.devops.settings.service.context.converter;

import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.converter
 * @since 11/26/2023 12:11 AM
 */
public abstract class AbstractCollectionTypeConverter extends AbstractObjectConverter {

    public AbstractCollectionTypeConverter(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @SuppressWarnings({"rawtypes"})
    protected Object collectionConvertValue(Class<? extends Collection> type, Object value, Field settingField) {
        Type[] actualTypeArguments = getActualTypeArguments(settingField);
        List<Class<?>> genericsTypes = getGenericsTypes(actualTypeArguments, applicationContext);
        if (Objects.nonNull(genericsTypes) && genericsTypes.size() == 1) {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            CollectionType collectionType = typeFactory.constructCollectionType(type, genericsTypes.get(0));
            return objectMapper.convertValue(value, collectionType);
        }
        return value;
    }

}
