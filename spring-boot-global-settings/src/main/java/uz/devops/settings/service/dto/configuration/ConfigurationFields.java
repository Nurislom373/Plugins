package uz.devops.settings.service.dto.configuration;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration
 * @since 11/23/2023 12:37 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationFields extends DefaultConfiguration implements Serializable {

    private List<Field> fields;

}
