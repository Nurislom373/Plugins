package uz.devops.observability.factories.observe;

import org.springframework.stereotype.Component;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.service.convention.NamingConventions;
import uz.devops.observability.service.observe.RegisteredObserve;
import uz.devops.observability.service.observe.RegisteredObserveContext;

import java.util.HashSet;
import java.util.Set;

import static uz.devops.observability.utils.BaseUtils.concat;

/**
 * @author Nurislom
 * @see uz.devops.observability.factories.observe
 * @since 12/19/2023 6:30 PM
 */
@Component
public class DefaultObserveMetricsNameFactory implements ObserveMetricNameFactory {

    private final NamingConventions namingConventions;
    private final RegisteredObserveContext observeMultiMetricsContext;

    public DefaultObserveMetricsNameFactory(NamingConventions namingConventions, RegisteredObserveContext observeMultiMetricsContext) {
        this.namingConventions = namingConventions;
        this.observeMultiMetricsContext = observeMultiMetricsContext;
    }

    @Override
    public Set<String> createMetricsName(String uri, Set<ObserveType> types) {
        Set<String> metricsName = new HashSet<>();
        types.forEach(type -> tryCreateMetricsName(uri, type, metricsName));
        return metricsName;
    }

    private void tryCreateMetricsName(String uri, ObserveType type, Set<String> metricsName) {
        if (type.isRegistered()) {
            namingConventionMultiMetrics(uri, type, metricsName);
        } else {
            metricsName.add(namingConventions.convention(uri, type));
        }
    }

    private void namingConventionMultiMetrics(String uri, ObserveType type, Set<String> metricsName) {
        observeMultiMetricsContext.get(type)
                .ifPresent(observeMultiMetrics -> {
                    String convention = namingConventions.convention(uri, type);
                    addMetricsName(metricsName, observeMultiMetrics, convention);
                });
    }

    private static void addMetricsName(Set<String> metricsName, RegisteredObserve observeMultiMetrics, String convention) {
        observeMultiMetrics.registeredMetricsSuffixes().forEach(meter -> {
            metricsName.add(concat(convention, meter));
        });
    }

}
