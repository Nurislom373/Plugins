package uz.devops.settings.service.context.fieldConfig;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import uz.devops.settings.service.context.GlobalSettingContext;
import uz.devops.settings.util.BaseUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static uz.devops.settings.util.BaseUtils.unwrapProxyBean;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context.fieldConfig
 * @since 11/26/2023 2:53 PM
 */
@Slf4j
@Service(SimpleGlobalSettingBeanFieldContext.NAME)
public class SimpleGlobalSettingBeanFieldContext implements GlobalSettingBeanFieldContext {

    public static final String NAME = "simpleGlobalSettingConfigContext";
    private final GlobalSettingContext globalSettingContext;
    private final SimpleGlobalSettingConfigAnnotatedBeanContext settingConfigAnnotatedBeanContext;

    public SimpleGlobalSettingBeanFieldContext(GlobalSettingContext globalSettingContext,
                                               SimpleGlobalSettingConfigAnnotatedBeanContext settingConfigAnnotatedBeanContext) {
        this.globalSettingContext = globalSettingContext;
        this.settingConfigAnnotatedBeanContext = settingConfigAnnotatedBeanContext;
    }

    public void afterPropertiesSet() {
        this.execute();
    }

    @Override
    public void execute() {
        Map<BeanDefinition, BeanAnnotatedField> definitionIntanceMap = settingConfigAnnotatedBeanContext.getBeanDefinitionIntanceMap();
        definitionIntanceMap.forEach(((beanDefinition, beanAnnotatedField) -> {
            beanAnnotatedField.getFields().forEach(field -> setBeanPropertyValue(beanAnnotatedField, field));
        }));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setBeanPropertyValue(BeanAnnotatedField beanAnnotatedField, Field field) {
        try {
            Object configuration = globalSettingContext.getConfiguration(BaseUtils.getFieldGenericTypes(field));
            if (Objects.nonNull(configuration)) {
                Object bean = unwrapProxyBean(beanAnnotatedField.getInstance());
                AtomicReference atomicReference = (AtomicReference) PropertyUtils.getProperty(bean, field.getName());
                atomicReference.set(configuration);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.warn("setBeanPropertyValue method exception thrown : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void copyProperties(Object source, Object target) {
//        ExpressionParser parser = new SpelExpressionParser();
//        StandardEvaluationContext context = new StandardEvaluationContext(source);
        try {
            for (var field : source.getClass().getDeclaredFields()) {
                String fieldName = field.getName();
                Object value = PropertyUtils.getProperty(source, fieldName);
//                Object value = parser.parseExpression(fieldName).getValue(context);
                if (value != null) {
                    PropertyUtils.setProperty(target, fieldName, value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
