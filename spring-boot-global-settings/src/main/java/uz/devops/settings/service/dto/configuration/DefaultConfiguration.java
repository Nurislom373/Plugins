package uz.devops.settings.service.dto.configuration;

import lombok.*;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration
 * @since 12/21/2023 4:38 PM
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefaultConfiguration implements Configuration {

    private Long id;
    private Long parentId; // optional field
    private String name;
    private List<Title> titles; // optional field

}
