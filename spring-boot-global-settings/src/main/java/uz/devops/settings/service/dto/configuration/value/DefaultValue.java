package uz.devops.settings.service.dto.configuration.value;

import lombok.*;
import uz.devops.settings.service.dto.configuration.Value;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration.value
 * @since 11/23/2023 1:55 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefaultValue implements Value {

    private String value;

}
