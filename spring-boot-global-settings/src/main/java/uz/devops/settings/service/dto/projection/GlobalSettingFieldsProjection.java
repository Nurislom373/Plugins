package uz.devops.settings.service.dto.projection;

import uz.devops.settings.domain.enumuration.InputType;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.projection
 * @since 11/23/2023 6:18 PM
 */
public interface GlobalSettingFieldsProjection {

    Long getId();

    String getFieldName();

    String getFieldType();

    InputType getInputType();

    String getFieldValue();

    String getFieldClassType();

    String getTitle();

}
