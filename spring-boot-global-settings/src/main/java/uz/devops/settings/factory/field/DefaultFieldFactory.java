package uz.devops.settings.factory.field;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.factory.title.TitleFactory;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.service.configuration.value.ConfigurationValueAdapter;
import uz.devops.settings.service.dto.FieldCreateDTO;
import uz.devops.settings.service.dto.configuration.Field;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.field
 * @since 12/21/2023 6:24 PM
 */
@Component
public class DefaultFieldFactory implements FieldFactory {

    private final TitleFactory titleFactory;
    private final ApplicationContext applicationContext;
    private final ConfigurationValueAdapter configurationValueAdapter;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;

    public DefaultFieldFactory(TitleFactory titleFactory, ApplicationContext applicationContext,
                               ConfigurationValueAdapter configurationValueAdapter, GlobalSettingFieldsRepository globalSettingFieldsRepository) {
        this.titleFactory = titleFactory;
        this.applicationContext = applicationContext;
        this.configurationValueAdapter = configurationValueAdapter;
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
    }

    @Override
    public List<Field> create(FieldCreateDTO fieldCreateDTO) {
        return createFields(fieldCreateDTO);
    }

    private List<Field> createFields(FieldCreateDTO dto) {
        Class<?> configClass = springClassLoader(dto.getImplementClass(), applicationContext);
        if (Objects.isNull(configClass)) {
            return Collections.emptyList();
        }
        return tryCreateFields(getConfigFields(dto), configClass);
    }

    private List<GlobalSettingFields> getConfigFields(FieldCreateDTO dto) {
        return globalSettingFieldsRepository.findAllByInfo_Id(dto.getGlobalSettingInfoId());
    }

    private List<Field> tryCreateFields(List<GlobalSettingFields> fields, Class<?> configClass) {
        return fields.stream().map(globalField -> createField(globalField, configClass))
                .collect(Collectors.toList());
    }

    private Field createField(GlobalSettingFields fields, Class<?> configurationClass) {
        return Field.builder()
                .id(fields.getId())
                .name(fields.getFieldName())
                .inputType(fields.getInputType())
                .titles(titleFactory.create(fields.getTitles()))
                .data(configurationValueAdapter.createValue(fields, configurationClass))
                .build();
    }

}
