package uz.devops.observability.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uz.devops.observability.interceptor.DefaultRestInterceptor;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 3:36 PM
 */
@Configuration
public class ObservabilityWebConfiguration implements WebMvcConfigurer {

    @Autowired
    private DefaultRestInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }

}
