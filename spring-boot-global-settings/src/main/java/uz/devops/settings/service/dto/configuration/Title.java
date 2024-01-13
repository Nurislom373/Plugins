package uz.devops.settings.service.dto.configuration;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration
 * @since 11/30/2023 12:07 PM
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Title {

    @JsonAlias("lang")
    @JsonProperty("lang")
    private String language;

    @JsonAlias("text")
    @JsonProperty("text")
    private String title;

}
