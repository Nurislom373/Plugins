package uz.devops.observability.models.observe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import static uz.devops.observability.models.observe.ObserveTypeProperties.*;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 2:30 PM
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public class ObserveTypeDTO {

    @JsonProperty(TYPE)
    private String type;

    @JsonProperty(DESCRIPTION)
    private String description;

}
