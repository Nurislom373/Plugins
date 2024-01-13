package uz.devops.settings.value;

import uz.devops.settings.converter.GlobalSettingValueConverter;
import uz.devops.settings.domain.enumuration.InputType;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;

import java.lang.reflect.Field;

public class GlobalSettingValue<T> {

    protected String name;
    protected String identifierName;
    protected GlobalSettingsImplementFieldInfo fieldInfo;
    protected String defaultValue;
    protected Class<T> settingValueClass;
    protected Field settingField;
    protected GlobalSettingValueConverter globalSettingValueConverter;
    protected InputType inputType;
    protected String fieldType;

    public GlobalSettingValue() {
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public InputType getInputType() {
        return this.inputType;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public Class getSettingValueClass() {
        return this.settingValueClass;
    }

    public void setSettingValueClass(Class value) {
        this.settingValueClass = value;
    }

    public Field getSettingField() {
        return this.settingField;
    }

    public void setSettingField(Field settingField) {
        this.settingField = settingField;
    }

    public GlobalSettingValueConverter getSettingValueConverter() {
        return this.globalSettingValueConverter;
    }

    public GlobalSettingsImplementFieldInfo getFieldInfo() {
        return this.fieldInfo;
    }

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    public void setFieldInfo(GlobalSettingsImplementFieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    public void setGlobalSettingValueConverter(GlobalSettingValueConverter globalSettingValueConverter) {
        this.globalSettingValueConverter = globalSettingValueConverter;
    }
}
