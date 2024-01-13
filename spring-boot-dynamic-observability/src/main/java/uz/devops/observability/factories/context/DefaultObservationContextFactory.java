package uz.devops.observability.factories.context;

import io.micrometer.observation.Observation;
import org.springframework.stereotype.Component;
import uz.devops.observability.service.collector.ContextNameCollector;
import uz.devops.observability.service.convention.NamingConventions;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/16/2023 12:17 PM
 */
@Component
public class DefaultObservationContextFactory implements ObservationContextFactory {

    private final ContextNameCollector contextNameCollector;
    private final NamingConventions namingConventions;

    public DefaultObservationContextFactory(ContextNameCollector contextNameCollector, NamingConventions namingConventions) {
        this.contextNameCollector = contextNameCollector;
        this.namingConventions = namingConventions;
    }

    @Override
    public Observation.Context create(String name) {
        Observation.Context context = new Observation.Context();
        context.setName(namingConventions.convention(name));
        contextNameCollector.add(context.getName());
        return context;
    }

}
