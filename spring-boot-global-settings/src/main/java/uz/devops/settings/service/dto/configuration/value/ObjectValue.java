package uz.devops.settings.service.dto.configuration.value;

import lombok.*;
import uz.devops.settings.service.dto.configuration.Value;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration.value
 * @since 11/28/2023 2:17 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ObjectValue implements Value {

    private Object value;

}
