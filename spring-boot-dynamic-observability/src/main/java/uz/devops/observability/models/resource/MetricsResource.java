package uz.devops.observability.models.resource;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability.models.resource
 * @since 12/19/2023 6:17 PM
 */
public interface MetricsResource extends Resource {

    Set<String> getMetricsName();

}
