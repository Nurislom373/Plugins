package uz.devops.settings;

import org.springframework.boot.test.context.SpringBootTest;
import uz.devops.settings.config.GlobalSettingConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {GlobalSettingsAutoConfiguration.class, GlobalSettingConfiguration.class})
public @interface IntegrationTest {
}
