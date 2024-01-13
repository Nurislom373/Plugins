package uz.devops.observability.service.observe;

import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.observe
 * @since 12/19/2023 6:11 PM
 */
public interface RegisteredObserve {

    Set<String> registeredMetricsSuffixes();

    ObserveType getType();

}
