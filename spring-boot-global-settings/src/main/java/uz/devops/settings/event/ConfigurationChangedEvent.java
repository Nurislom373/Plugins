package uz.devops.settings.event;

import lombok.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.context.ApplicationEvent;

/**
 * @author Nurislom
 * @see uz.devops.settings.event
 * @since 11/28/2023 4:44 PM
 */
@Getter
@Setter
@ToString
public class ConfigurationChangedEvent extends ApplicationEvent {

    private Object value;
    private String fieldName;
    private Class<?> valueType;
    private Class<?> implementClass;

    public ConfigurationChangedEvent(Object source) {
        super(source);
    }

    public ConfigurationChangedEvent value(Object value) {
        this.value = value;
        return this;
    }

    public ConfigurationChangedEvent fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public ConfigurationChangedEvent valueType(Class<?> valueType) {
        this.valueType = valueType;
        return this;
    }

    public ConfigurationChangedEvent implementClass(Class<?> implementClass) {
        this.implementClass = implementClass;
        return this;
    }

}
