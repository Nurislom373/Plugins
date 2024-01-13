package uz.devops.observability.service.observe;

import org.springframework.stereotype.Service;
import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.observe
 * @since 12/19/2023 6:13 PM
 */
@Service
public class TimerRegisteredObserve implements RegisteredObserve {

    @Override
    public Set<String> registeredMetricsSuffixes() {
        return Set.of("_seconds_max", "_seconds_sum", "_seconds_count");
    }

    @Override
    public ObserveType getType() {
        return ObserveType.TIMER;
    }

}
