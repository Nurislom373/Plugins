package uz.devops.settings.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager
 * @since 11/22/2023 1:25 PM
 */
@Slf4j
public abstract class GlobalSettingsDataUtils {

    public static Object getFieldValueV3(Field field, Object instance) {
        try {
            if (field.trySetAccessible()) {
                return field.get(instance);
            }
        } catch (ReflectiveOperationException | RuntimeException e) {
            log.warn("getFieldValueV3 exception thrown: {}", e.getMessage());
            e.printStackTrace();
        }
        return getFieldValueV2(field, instance);
    }

    public static Object getFieldValueV2(Field field, Object instance) {
        for (Method method : instance.getClass().getDeclaredMethods()) {
            if (isBooleanType(field)) {
                if (checkStartWithIsMethod(field, method)) {
                    return invokeMethod(method, instance);
                }
            }
            if (checkStartWithGetMethod(field, method)) {
                return invokeMethod(method, instance);
            }
        }
        return null;
    }

    public static void setFieldValue(Field field, Object instance, Object value) {
        for (Method declaredMethod : instance.getClass().getDeclaredMethods()) {
            if (checkStartWithSetMethod(field, declaredMethod)) {
                invokeVoidMethod(declaredMethod, instance, value);
                break;
            }
        }
    }

    public static void setStaticFieldValue(Field field, Object value) {
        try {
            if (field.trySetAccessible()) {
                field.set(null, value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isBooleanType(Field field) {
        return field.getType().equals(Boolean.class) || field.getType().equals(boolean.class);
    }

    private static boolean checkStartWithSetMethod(Field field, Method method) {
        return method.getName().startsWith("set") && method.getName().toLowerCase().endsWith(field.getName().toLowerCase());
    }

    private static boolean checkStartWithGetMethod(Field field, Method method) {
        return method.getName().startsWith("get") && method.getName().toLowerCase().endsWith(field.getName().toLowerCase());
    }

    private static boolean checkStartWithIsMethod(Field field, Method method) {
        return method.getName().startsWith("is") && method.getName().toLowerCase().endsWith(field.getName().toLowerCase());
    }

    private static Object invokeMethod(Method method, Object instance) {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info("Could not determine method: " + method.getName());
            e.printStackTrace();
        }
        return null;
    }

    private static Object invokeMethod(Method method, Object instance, Object arg) {
        try {
            return method.invoke(instance, arg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.info("Could not determine method: " + method.getName());
        }
        return null;
    }

    private static void invokeVoidMethod(Method method, Object instance, Object arg) {
        try {
            if (method.trySetAccessible()) {
                Class<?> parameterType = method.getParameterTypes()[0];
                if (parameterType.isPrimitive()) {
                    primitiveCastInvokeMethod(method, instance, arg, parameterType);
                }
                method.invoke(instance, arg);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.warn("invokeVoidMethod exception thrown : {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private static void primitiveCastInvokeMethod(Method method, Object instance, Object arg, Class<?> methodArg) {
        try {
            if (methodArg.equals(boolean.class)) {
                method.invoke(instance, BooleanUtils.toBoolean((Boolean) arg));
            } else if (methodArg.equals(double.class)) {
                method.invoke(instance, NumberUtils.toDouble(String.valueOf(arg)));
            } else if (methodArg.equals(int.class)) {
                method.invoke(instance, NumberUtils.toDouble(String.valueOf(arg)));
            } else if (methodArg.equals(long.class)) {
                method.invoke(instance, NumberUtils.toLong(String.valueOf(arg)));
            } else if (methodArg.equals(short.class)) {
                method.invoke(instance, NumberUtils.toShort(String.valueOf(arg)));
            } else if (methodArg.equals(byte.class)) {
                method.invoke(instance, NumberUtils.toByte(String.valueOf(arg)));
            } else if (methodArg.equals(float.class)) {
                method.invoke(instance, NumberUtils.toFloat(String.valueOf(arg)));
            } else if (methodArg.equals(char.class)) {
                method.invoke(instance, CharUtils.toChar((Character) arg));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.warn("primitiveCastInvokeMethod exception thrown : {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
