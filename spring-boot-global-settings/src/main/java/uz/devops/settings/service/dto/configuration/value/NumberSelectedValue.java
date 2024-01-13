package uz.devops.settings.service.dto.configuration.value;

import lombok.*;
import uz.devops.settings.service.dto.configuration.Value;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration.value
 * @since 11/23/2023 4:14 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NumberSelectedValue implements Value {

    private List<Number> value;

}
