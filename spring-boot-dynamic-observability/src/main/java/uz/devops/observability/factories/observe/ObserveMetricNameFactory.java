package uz.devops.observability.factories.observe;

import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability.factories.observe
 * @since 12/19/2023 6:30 PM
 */
public interface ObserveMetricNameFactory {

    Set<String> createMetricsName(String uri, Set<ObserveType> types);

}
