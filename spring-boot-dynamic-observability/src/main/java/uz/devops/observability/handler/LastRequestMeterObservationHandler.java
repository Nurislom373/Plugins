package uz.devops.observability.handler;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.observation.Observation;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.stereotype.Component;
import uz.devops.observability.interceptor.AbstractObservationHandler;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.factories.request.LastRequestStartTimeFactory;
import uz.devops.observability.models.request.LastRequestStartTime;
import uz.devops.observability.service.collector.ContextNameCollector;
import uz.devops.observability.utils.MeterSuffixes;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static uz.devops.observability.utils.BaseUtils.*;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 2:36 PM
 */
@Component
public class LastRequestMeterObservationHandler extends AbstractObservationHandler {

    private static final String METER_SUFFIX = MeterSuffixes.LAST_REQUEST;
    private final LastRequestStartTimeFactory startTimeFactory;

    public LastRequestMeterObservationHandler(PrometheusMeterRegistry prometheusMeterRegistry, LastRequestStartTimeFactory startTimeFactory,
                                              ContextNameCollector contextNameCollector) {
        super(contextNameCollector, prometheusMeterRegistry);
        this.startTimeFactory = startTimeFactory;
    }

    @Override
    public void onStart(Observation.Context context) {
        checkTrueThen(context, () -> {
            LastRequestStartTime startTime = startTimeFactory.create(Instant.now());
            context.put(LastRequestStartTime.class, startTime);
        });
    }

    @Override
    public void onStop(Observation.Context context) {
        checkTrueThen(context, () -> {
            Instant endTime = Instant.now();
            LastRequestStartTime startTime = context.getRequired(LastRequestStartTime.class);
            gaugeRegistry(context, startTime.getStartTime(), endTime, getTags(context));
        });
    }

    private void gaugeRegistry(Observation.Context context, Instant startTime, Instant endTime, List<Tag> tags) {
        Search search = meterRegistry.find(concat(context.getName(), METER_SUFFIX));
        Gauge gauge = search.gauge();
        if (Objects.nonNull(gauge)) {
            meterRegistry.removeByPreFilterId(gauge.getId());
        }
        meterRegistry.gauge(concat(context.getName(), METER_SUFFIX), millisSecondsToDouble(betweenMillisSeconds(startTime, endTime)));
    }

    @Override
    public ObserveType observeType() {
        return ObserveType.LAST_REQUEST;
    }

}
