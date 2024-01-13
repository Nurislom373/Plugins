package uz.devops.settings.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * @author Nurislom
 * @see uz.devops.settings.util
 * @since 11/18/2023 3:45 PM
 */
@Slf4j
public abstract class BaseUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper() {{
        registerModule(new JavaTimeModule());
    }};
    private static final Map<String, Class<?>> primitivesMap = new HashMap<>();

    public static boolean isPrimitiveClass(String primitiveClass) {
        return primitivesMap.containsKey(primitiveClass);
    }

    public static Class<?> primitiveToWrapper(String primitiveClass) {
        return primitivesMap.get(primitiveClass);
    }

    public static Object unwrapProxyBean(Object proxyBean) {
        try {
            if (AopUtils.isAopProxy(proxyBean) && proxyBean instanceof Advised) {
                return ((Advised) proxyBean).getTargetSource().getTarget();
            }
        } catch (Exception e) {
            log.warn("unwrapProxyBean exception thrown : {}", e.getMessage());
            e.printStackTrace();
        }
        return proxyBean;
    }

    public static boolean checkTypeAnnotationPresent(Class<?> type, Class<? extends Annotation> annotation) {
        return type.isAnnotationPresent(annotation);
    }

    public static <T extends Annotation> T getAnnotationWithType(Class<?> type, Class<T> annotation) {
        return type.getAnnotation(annotation);
    }

    public static Class<?> getFieldGenericTypes(Field field) {
        return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
    }

    public static boolean isCollection(Object value) {
        return value instanceof Map || value instanceof Collection;
    }

    public static String createFieldName(GlobalSettingsImplementFieldInfo fieldInfo) {
        return fieldInfo.getGlobalSettingsImplementInfo().getImplementClass().getSimpleName()
                + "." + fieldInfo.getGlobalSettingValue().getSettingField().getName();
    }

    public static Optional<Field> findMatchField(Class<?> configClass, Object instance) {
        return Arrays.stream(instance.getClass().getDeclaredFields())
                .filter( field -> Objects.equals(configClass, field.getType())).findFirst();
    }

    public static boolean existMatchKey(List<Class<?>> values, Class<?> settingType) {
        return values.contains(settingType) || values.stream().anyMatch(key -> key.isAssignableFrom(settingType));
    }

    static {
        primitivesMap.put("byte", Byte.class);
        primitivesMap.put("short", Short.class);
        primitivesMap.put("int", Integer.class);
        primitivesMap.put("long", Long.class);
        primitivesMap.put("float", Float.class);
        primitivesMap.put("double", Float.class);
        primitivesMap.put("char", Character.class);
        primitivesMap.put("boolean", Boolean.class);
    }

}
