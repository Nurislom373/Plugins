package uz.devops.settings.service.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto
 * @since 11/28/2023 10:51 AM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateField {

    @Min(1)
    @NotNull
    private Long id;

    @NotNull
    private Object value;

}
