package uz.devops.observability.models.resource.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static uz.devops.observability.models.resource.dto.ObserveProperties.METHOD;
import static uz.devops.observability.models.resource.dto.ObserveProperties.URI;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 4:33 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ObserveDeleteDTO {

    @NotBlank
    @JsonProperty(URI)
    private String uri;

    @NotNull
    @JsonProperty(METHOD)
    private RequestMethod method;

}
