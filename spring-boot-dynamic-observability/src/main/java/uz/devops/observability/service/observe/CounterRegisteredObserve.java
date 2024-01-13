package uz.devops.observability.service.observe;

import org.springframework.stereotype.Service;
import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.observe
 * @since 12/20/2023 12:15 PM
 */
@Service
public class CounterRegisteredObserve implements RegisteredObserve {

    @Override
    public Set<String> registeredMetricsSuffixes() {
        return Set.of("_total");
    }

    @Override
    public ObserveType getType() {
        return ObserveType.COUNTER;
    }

}
