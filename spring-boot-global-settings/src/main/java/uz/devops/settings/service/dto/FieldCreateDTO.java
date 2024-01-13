package uz.devops.settings.service.dto;

import lombok.*;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto
 * @since 12/21/2023 6:20 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldCreateDTO {

    private String implementClass;
    private Long globalSettingInfoId;

}
