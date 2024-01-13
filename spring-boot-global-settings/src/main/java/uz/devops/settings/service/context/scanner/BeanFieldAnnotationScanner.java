package uz.devops.settings.service.context.scanner;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import uz.devops.settings.annotation.GlobalSettingConfig;
import uz.devops.settings.service.configuration.ConfigurationServiceUtils;
import uz.devops.settings.service.context.fieldConfig.BeanAnnotatedField;
import uz.devops.settings.service.context.fieldConfig.SimpleGlobalSettingBeanFieldContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static uz.devops.settings.service.configuration.ConfigurationServiceUtils.springClassLoader;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context
 * @since 11/26/2023 3:25 PM
 */
public abstract class BeanFieldAnnotationScanner extends AbstractScanner {


    /**
     * This method is used to retrieve the {@link GlobalSettingConfig} annotation beans set to the field.
     *
     * @param applicationContext - beanFactory
     * @param annotationType - annotation to search
     * @return bean definition and bean instance
     */
    public static Map<BeanDefinition, BeanAnnotatedField> findBeansWithFieldAnnotation(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        Map<BeanDefinition, BeanAnnotatedField> result = new ConcurrentHashMap<>();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        BeanDefinitionRegistry beanDefinitionRegistry = getBeanDefinitionRegistry(applicationContext);

        Arrays.stream(beanDefinitionNames)
                .filter(applicationContext::containsBean)
                .filter(applicationContext::isSingleton)
                .forEach(beanName -> {
                    BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanName);
                    if (Objects.nonNull(beanDefinition.getScope()) && isNotAcceptedScope(beanDefinition.getScope())) {
                        return;
                    }
                    if (!Objects.equals(SimpleGlobalSettingBeanFieldContext.NAME, beanName)) {
                        Object bean = applicationContext.getBean(beanName);

                        Class<?> beanClass = null;
                        boolean isValidBean = true;
                        if (isProxyClass(bean)) {
                            if (bean instanceof Advised) {
                                beanClass = ((Advised) bean).getTargetSource().getTargetClass();
                            } else {
                                isValidBean = false;
                            }
                        } else {
                            beanClass = springClassLoader(beanDefinition.getBeanClassName(), applicationContext);
                        }

                        if (isValidBean) {
                            BeanAnnotatedField annotatedField = new BeanAnnotatedField()
                                    .instance(bean)
                                    .beanName(beanName)
                                    .beanDefinition(beanDefinition)
                                    .fields(new CopyOnWriteArrayList<>());

                            boolean annotatedFieldFound = false;

                            if (Objects.nonNull(beanClass)) {
                                // Scan fields of the bean class
                                Field[] fields = beanClass.getDeclaredFields();
                                if (fields.length >= 1) {
                                    for (Field field : fields) {
                                        if (Objects.equals(field.getType(), AtomicReference.class) &&
                                                field.isAnnotationPresent(annotationType)) {
                                            annotatedFieldFound = true;
                                            annotatedField.getFields().add(field);
                                        }
                                    }
                                }
                            }

                            if (annotatedFieldFound) {
                                result.put(beanDefinition, annotatedField);
                            }
                        }
                    }
                });
        return result;
    }

}
