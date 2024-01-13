package uz.devops.settings.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(GlobalSettingTitleContainer.class)
public @interface GlobalSettingTitle {
    String language();

    String title();
}
