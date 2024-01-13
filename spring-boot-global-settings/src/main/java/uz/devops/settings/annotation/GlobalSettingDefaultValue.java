package uz.devops.settings.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalSettingDefaultValue {
    String defaultValue();
}

