package uz.devops.settings.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.function.Supplier;

import static uz.devops.settings.util.BaseUtils.OBJECT_MAPPER;

/**
 * @author Nurislom
 * @see uz.devops.settings.converter
 * @since 11/18/2023 4:01 PM
 */
public abstract class AbstractGlobalSettingValueConverter implements GlobalSettingValueConverter {

    protected final ObjectMapper objectMapper = OBJECT_MAPPER;

    protected boolean check(Object value) {
        return Objects.nonNull(value);
    }

    protected boolean check(String value) {
        return Objects.nonNull(value) && !value.isBlank();
    }

    protected <R> R ifCheckFail(Supplier<Boolean> checkSupplier, ThrowsSupplier<R> functionSupplier) throws Exception {
        if (!checkSupplier.get()) {
            return null;
        }
        return functionSupplier.get();
    }

    public interface ThrowsSupplier<T> {

        T get() throws Exception;

    }

}
