package uz.devops.observability.interceptor;

import io.micrometer.core.instrument.Tag;
import io.micrometer.observation.Observation;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import uz.devops.observability.IObservationHandler;
import uz.devops.observability.service.collector.ContextNameCollector;

import java.util.List;
import java.util.Objects;

import static uz.devops.observability.utils.BaseUtils.createTags;
import static uz.devops.observability.utils.BaseUtils.getErrorValue;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 4:27 PM
 */
public abstract class AbstractObservationHandler implements IObservationHandler {

    protected final ContextNameCollector contextNameCollector;
    protected final PrometheusMeterRegistry meterRegistry;
    private AbstractObservationHandler nextObservationHandler;

    public AbstractObservationHandler(ContextNameCollector contextNameCollector, PrometheusMeterRegistry prometheusMeterRegistry) {
        this.contextNameCollector = contextNameCollector;
        this.meterRegistry = prometheusMeterRegistry;
    }

    public AbstractObservationHandler(ContextNameCollector contextNameCollector, PrometheusMeterRegistry prometheusMeterRegistry,
                                      AbstractObservationHandler nextObservationHandler) {
        this.contextNameCollector = contextNameCollector;
        this.meterRegistry = prometheusMeterRegistry;
        this.nextObservationHandler = nextObservationHandler;
    }

    public AbstractObservationHandler next(AbstractObservationHandler nextObservationHandler) {
        this.nextObservationHandler = nextObservationHandler;
        return this;
    }

    @Override
    public void onStart(Observation.Context context, boolean moveToNext) {
        onStart(context);
        nextObservationHandlerOnStart(context, moveToNext);
    }

    @Override
    public void onStop(Observation.Context context, boolean moveToNext) {
        onStop(context);
        nextObservationHandlerOnStop(context, moveToNext);
    }

    protected void checkTrueThen(Observation.Context context, Runnable runnable) {
        if (contextNameCollector.contains(context.getName())) {
            runnable.run();
        }
    }

    private void nextObservationHandlerOnStart(Observation.Context context, boolean moveToNext) {
        nextObservationHandlerOnRunnable(moveToNext, () -> nextObservationHandler.onStart(context, true));
    }

    private void nextObservationHandlerOnStop(Observation.Context context, boolean moveToNext) {
        nextObservationHandlerOnRunnable(moveToNext, () -> nextObservationHandler.onStop(context, true));
    }

    private void nextObservationHandlerOnRunnable(boolean moveToNext, Runnable runnable) {
        if (Objects.nonNull(nextObservationHandler) && moveToNext) {
            runnable.run();
        }
    }

    protected final List<Tag> getTags(Observation.Context context) {
        List<Tag> tags = createTags(context);
        tags.add(Tag.of("error", getErrorValue(context)));
        return tags;
    }

}
