package uz.devops.settings.service.context.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.converter
 * @since 11/25/2023 11:57 PM
 */
@Slf4j
@Service
public class ListObjectConverter extends AbstractCollectionTypeConverter {

    public ListObjectConverter(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public Object convert(Object value, Field settingField) {
        return collectionConvertValue(ArrayList.class, value, settingField);
    }

    @Override
    public Class<?> getType() {
        return List.class;
    }

}
