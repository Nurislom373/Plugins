package uz.devops.observability;

import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/19/2023 2:10 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = {DynamicObservabilityAutoConfiguration.class, ObservabilityConfiguration.class},
        properties = {
                "management.endpoints.web.exposure.include=*"
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public @interface ObservabilityTest {
}
