package uz.devops.observability.factories.context;

import io.micrometer.observation.Observation;
import uz.devops.observability.factories.GenericFactory;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/16/2023 12:17 PM
 */
public interface ObservationContextFactory extends GenericFactory<String, Observation.Context> {
}
