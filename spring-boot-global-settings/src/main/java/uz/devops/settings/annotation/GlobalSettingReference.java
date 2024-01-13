package uz.devops.settings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nurislom
 * @see uz.devops.settings.annotation
 * @since 11/29/2023 1:11 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalSettingReference {

    Class<?> configClass();

    String propertyPath();

}
