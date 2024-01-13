package uz.devops.settings.service.context.change;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;
import uz.devops.settings.event.ConfigurationChangedEvent;
import uz.devops.settings.service.context.CollectionValueConverterDelegate;
import uz.devops.settings.service.context.GlobalSettingContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static uz.devops.settings.util.BaseUtils.isCollection;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.change
 * @since 11/28/2023 5:03 PM
 */
@Slf4j
@Service
public class DefaultConfigurationChangeService implements ConfigurationChangeService {

    private final GlobalSettingContext globalSettingContext;
    private final CollectionValueConverterDelegate collectionValueConverter;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public DefaultConfigurationChangeService(GlobalSettingContext globalSettingContext, CollectionValueConverterDelegate collectionValueConverter) {
        this.globalSettingContext = globalSettingContext;
        this.collectionValueConverter = collectionValueConverter;
    }

    @Override
    public void changeConfigurations(ConfigurationChangedEvent event) {
        readWriteLock.writeLock().lock();
        try {
            Object configuration = globalSettingContext.getConfiguration(event.getImplementClass());
            if (Objects.nonNull(configuration)) {
                tryChangeConfiguration(event, configuration);
            } else {
                log.warn("changed configuration instance not found ConfigurationContext!");
                throw new RuntimeException("changed configuration instance not found ConfigurationContext!");
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private void tryChangeConfiguration(ConfigurationChangedEvent event, Object configuration) {
        get(event, configuration.getClass().getDeclaredFields())
                .ifPresentOrElse(field -> {
                    try {
                        Object value = event.getValue();
                        if (isCollection(value)) {
                            value = collectionValueConverter.linkedMapConvertToGenericCollection(value, field);
                        }
                        PropertyUtils.setProperty(configuration, field.getName(), value);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        log.warn("configuration change value exception thrown : {}", e.getMessage());
                        throw new RuntimeException("configuration change value exception thrown!");
                    }
                }, () -> log.warn("configuration match field not found!"));
    }

    private Optional<Field> get(ConfigurationChangedEvent event, Field[] declaredFields) {
        return Arrays.stream(declaredFields).filter(field -> {
            return Objects.equals(field.getName(), event.getFieldName());
        }).findFirst();
    }

}
