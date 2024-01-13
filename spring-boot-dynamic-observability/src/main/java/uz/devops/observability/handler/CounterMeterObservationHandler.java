package uz.devops.observability.handler;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.observation.Observation;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.stereotype.Component;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.interceptor.AbstractObservationHandler;
import uz.devops.observability.service.collector.ContextNameCollector;
import uz.devops.observability.utils.MeterSuffixes;

import java.util.Objects;

import static uz.devops.observability.utils.BaseUtils.concat;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 1:02 PM
 */
@Component
public class CounterMeterObservationHandler extends AbstractObservationHandler {

    private static final String METER_SUFFIX = MeterSuffixes.COUNTER;

    public CounterMeterObservationHandler(ContextNameCollector contextNameCollector, PrometheusMeterRegistry prometheusMeterRegistry) {
        super(contextNameCollector, prometheusMeterRegistry);
    }

    @Override
    public void onStart(Observation.Context context) {
        checkTrueThen(context, () -> {
            Counter counter = getCounter(context);
            counter.increment();
        });
    }

    private Counter getCounter(Observation.Context context) {
        Search search = meterRegistry.find(context.getName());
        Counter counter = search.counter();
        if (Objects.isNull(counter)) {
            counter = registerNewCounter(context);
        }
        return counter;
    }

    private Counter registerNewCounter(Observation.Context context) {
        return meterRegistry.counter(concat(context.getName(), METER_SUFFIX), getTags(context));
    }

    @Override
    public ObserveType observeType() {
        return ObserveType.COUNTER;
    }

}
