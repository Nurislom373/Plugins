package uz.devops.settings.service.context.fieldConfig;

import org.springframework.beans.factory.config.BeanDefinition;

import java.util.List;
import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.fieldConfig
 * @since 11/29/2023 4:58 PM
 */
public interface GlobalSettingConfigAnnotatedBeanContext {

    Map<BeanDefinition, BeanAnnotatedField> getBeanDefinitionIntanceMap();

    List<BeanAnnotatedField> findByConfigClass(Class<?> configClass);

}
