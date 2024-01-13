package uz.devops.settings.service.context.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.converter
 * @since 11/26/2023 12:09 AM
 */
@Slf4j
@Service
public class SetObjectConverter extends AbstractCollectionTypeConverter {

    public SetObjectConverter(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object convert(Object value, Field settingField) {
        return collectionConvertValue(HashSet.class, value, settingField);
    }

    @Override
    public Class<?> getType() {
        return Set.class;
    }

}
