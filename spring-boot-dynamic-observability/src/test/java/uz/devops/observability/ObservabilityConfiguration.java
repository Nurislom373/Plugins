package uz.devops.observability;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/19/2023 2:07 PM
 */
@TestConfiguration
public class ObservabilityConfiguration {

    @Bean
    MeterRegistry promethuesMeterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

}
