package uz.devops.observability.models.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

import static uz.devops.observability.models.resource.ResourceProperties.METRICS_NAME;

/**
 * @author Nurislom
 * @see uz.devops.observability.models.resource.dto
 * @since 12/19/2023 6:18 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DefaultMetricsResource extends DefaultResource implements MetricsResource {

    @JsonProperty(METRICS_NAME)
    private Set<String> metricsName;

    public DefaultMetricsResource(String name, String uri, RequestMethod method, Set<ObserveType> observeTypes, Set<String> metricsName) {
        super(name, uri, method, observeTypes);
        this.metricsName = metricsName;
    }

}
