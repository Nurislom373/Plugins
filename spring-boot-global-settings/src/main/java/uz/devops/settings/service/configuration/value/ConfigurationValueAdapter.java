package uz.devops.settings.service.configuration.value;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.enumuration.FieldType;
import uz.devops.settings.service.dto.configuration.Value;
import uz.devops.settings.service.dto.configuration.value.DefaultValue;
import uz.devops.settings.util.BaseUtils;

import java.util.*;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration.value
 * @since 11/23/2023 7:13 PM
 */
@Slf4j
@Service
public class ConfigurationValueAdapter implements InitializingBean {

    private final List<Class<?>> collections = List.of(
            Map.class, Collection.class
    );

    private final List<FieldType> defaultTypes = List.of(
            FieldType.TEXT, FieldType.NUMBER, FieldType.JSON, FieldType.DATETIME, FieldType.BOOLEAN
    );

    private final ApplicationContext applicationContext;
    private final ConfigurationValueStrategy defaultValueStrategy;
    private final ConfigurationValueStrategy collectionValueStrategy;
    private final Map<FieldType, ConfigurationValueStrategy> valueStrategyMap = new HashMap<>();

    protected ConfigurationValueAdapter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.defaultValueStrategy = new DefaultConfigurationValueStrategy(applicationContext);
        this.collectionValueStrategy = new CollectionConfigurationValueStrategy(applicationContext);
    }

    // TODO null value create Value interface implement
    public final Value createValue(GlobalSettingFields fields, Class<?> configurationClass) {
        FieldType fieldType = FieldType.valueOf(fields.getFieldType());
        if (Objects.isNull(fields.getFieldValue())) {
            return new DefaultValue();
        }
        if (isDefault(fieldType)) {
            if (isCollection(fields, fieldType)) {
                return collectionValueStrategy.create(fields, configurationClass);
            }
            return defaultValueStrategy.create(fields, configurationClass);
        } else {
            return valueStrategyMap.get(fieldType).create(fields, configurationClass);
        }
    }

    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(ConfigurationValueStrategy.class)
                .forEach((beanName, bean) -> {
                    if (Objects.nonNull(bean.getKey())) {
                        valueStrategyMap.put(bean.getKey(), bean);
                    }
                });
    }

    private boolean isDefault(FieldType type) {
        return defaultTypes.contains(type);
    }

    private boolean isCollection(GlobalSettingFields fields, FieldType fieldType) {
        if (Objects.equals(fieldType, FieldType.JSON)) {
            Class<?> fieldClassType = springClassLoader(fields.getFieldClassType(), applicationContext);
            return BaseUtils.existMatchKey(collections, fieldClassType);
        }
        return false;
    }

}
