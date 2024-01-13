package uz.devops.observability.models.resource.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.enumeration.ObserveType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static uz.devops.observability.models.resource.dto.ObserveProperties.*;


/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 3:13 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ObserveCreateDTO {

    @NotBlank
    @JsonProperty(URI)
    private String uri;

    @NotBlank
    @JsonProperty(NAME)
    private String name;

    @NotNull
    @JsonProperty(METHOD)
    private RequestMethod method;

    @NotNull
    @Size(min = 1)
    @JsonProperty(OBSERVE_TYPES)
    private Set<ObserveType> observeTypes;

}
