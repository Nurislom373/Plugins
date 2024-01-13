package uz.devops.observability.models.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

import static uz.devops.observability.models.resource.ResourceProperties.*;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 6:22 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResource implements Resource {

    @JsonProperty(NAME)
    private String name;

    @JsonProperty(URI)
    private String uri;

    @JsonProperty(METHOD)
    private RequestMethod method;

    @JsonProperty(OBSERVE_TYPES)
    private Set<ObserveType> observeTypes;

}
