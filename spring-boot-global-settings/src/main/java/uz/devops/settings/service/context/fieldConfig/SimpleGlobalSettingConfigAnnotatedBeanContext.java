package uz.devops.settings.service.context.fieldConfig;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.annotation.GlobalSettingConfig;
import uz.devops.settings.service.packages.GlobalSettingsPackagesService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static uz.devops.settings.service.context.scanner.BeanFieldAnnotationScanner.findBeansWithFieldAnnotation;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.fieldConfig
 * @since 11/26/2023 3:30 PM
 */
@Service
public class SimpleGlobalSettingConfigAnnotatedBeanContext implements GlobalSettingConfigAnnotatedBeanContext {

    private final Map<BeanDefinition, BeanAnnotatedField> beanDefinitionIntanceMap = new ConcurrentHashMap<>();
    private final ApplicationContext applicationContext;
    private boolean isCollectedBeans = false;

    public SimpleGlobalSettingConfigAnnotatedBeanContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<BeanDefinition, BeanAnnotatedField> getBeanDefinitionIntanceMap() {
        if (!isCollectedBeans) {
            collectBeanDefinitions();
        }
        return this.beanDefinitionIntanceMap;
    }

    @Override
    public List<BeanAnnotatedField> findByConfigClass(Class<?> configClass) {
        return beanDefinitionIntanceMap.values().stream().filter(beanAnnotatedField -> beanAnnotatedField.getFields()
                .stream().anyMatch(field -> Objects.equals(field.getType(), configClass))).collect(Collectors.toList());
    }

    private void collectBeanDefinitions() {
        beanDefinitionIntanceMap.putAll(findBeansWithFieldAnnotation(applicationContext, GlobalSettingConfig.class));
        this.isCollectedBeans = true;
    }

}
