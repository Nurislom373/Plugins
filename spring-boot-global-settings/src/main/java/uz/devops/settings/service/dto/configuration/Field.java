package uz.devops.settings.service.dto.configuration;

import lombok.*;
import uz.devops.settings.domain.enumuration.InputType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration
 * @since 11/23/2023 12:37 PM
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Field implements Serializable {

    private Long id;
    private String name;
    private List<Title> titles;
    private Value data;
    private InputType inputType;

}
