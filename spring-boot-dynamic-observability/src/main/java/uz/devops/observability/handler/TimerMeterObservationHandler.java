package uz.devops.observability.handler;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.stereotype.Component;
import uz.devops.observability.interceptor.AbstractObservationHandler;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.service.collector.ContextNameCollector;
import uz.devops.observability.utils.MeterSuffixes;

import java.util.List;

import static uz.devops.observability.utils.BaseUtils.concat;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 4:10 PM
 */
@Component
public class TimerMeterObservationHandler extends AbstractObservationHandler {

    private static final String METER_NAME_SUFFIX = MeterSuffixes.TIMER;

    public TimerMeterObservationHandler(PrometheusMeterRegistry meterRegistry, ContextNameCollector contextNameCollector) {
        super(contextNameCollector, meterRegistry);
    }

    @Override
    public void onStart(Observation.Context context) {
        checkTrueThen(context, () -> {
            Timer.Sample sample = Timer.start(meterRegistry);
            context.put(Timer.Sample.class, sample);
        });
    }

    @Override
    public void onStop(Observation.Context context) {
        checkTrueThen(context, () -> {
            Timer.Sample sample = context.getRequired(Timer.Sample.class);
            timerRegistry(context, sample, getTags(context));
        });
    }

    private void timerRegistry(Observation.Context context, Timer.Sample sample, List<Tag> tags) {
        sample.stop(Timer.builder(concat(context.getName(), METER_NAME_SUFFIX))
                .tags(tags).register(this.meterRegistry));
    }

    @Override
    public ObserveType observeType() {
        return ObserveType.TIMER;
    }

}
