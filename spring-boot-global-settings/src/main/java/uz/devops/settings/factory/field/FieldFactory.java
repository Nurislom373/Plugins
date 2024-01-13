package uz.devops.settings.factory.field;

import uz.devops.settings.factory.GenericFactory;
import uz.devops.settings.service.dto.FieldCreateDTO;
import uz.devops.settings.service.dto.configuration.Field;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.field
 * @since 12/21/2023 6:17 PM
 */
public interface FieldFactory extends GenericFactory<FieldCreateDTO, List<Field>> {
}
