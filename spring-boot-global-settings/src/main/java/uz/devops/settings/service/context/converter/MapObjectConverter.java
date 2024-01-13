package uz.devops.settings.service.context.converter;

import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.converter
 * @since 11/25/2023 11:38 PM
 */
@Service
public class MapObjectConverter extends AbstractObjectConverter {

    public MapObjectConverter(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object convert(Object value, Field settingField) {
        Type[] actualTypeArguments = getActualTypeArguments(settingField);
        List<Class<?>> genericsTypes = getGenericsTypes(actualTypeArguments, applicationContext);
        if (Objects.nonNull(genericsTypes) && genericsTypes.size() == 2) {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            MapType mapType = typeFactory.constructMapType(HashMap.class, genericsTypes.get(0), genericsTypes.get(1));
            return objectMapper.convertValue(value, mapType);
        }
        return value;
    }

    @Override
    public Class<?> getType() {
        return Map.class;
    }

}
