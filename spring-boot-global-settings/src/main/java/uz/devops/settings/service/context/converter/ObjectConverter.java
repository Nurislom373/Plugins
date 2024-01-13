package uz.devops.settings.service.context.converter;

import java.lang.reflect.Field;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.converter
 * @since 11/25/2023 11:37 PM
 */
public interface ObjectConverter {

    Object convert(Object value, Field settingField);

    Class<?> getType();

}
