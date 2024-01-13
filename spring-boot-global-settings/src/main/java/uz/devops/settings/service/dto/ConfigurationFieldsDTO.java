package uz.devops.settings.service.dto;

import lombok.*;
import uz.devops.settings.service.dto.configuration.Configuration;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto
 * @since 12/21/2023 4:53 PM
 */
@Getter
@Setter
@ToString
public class ConfigurationFieldsDTO extends FieldCreateDTO {

    private Configuration configuration;

    public ConfigurationFieldsDTO(Configuration configuration, Long globalSettingInfoId, String implementClass) {
        super(implementClass, globalSettingInfoId);
        this.configuration = configuration;
    }

}
