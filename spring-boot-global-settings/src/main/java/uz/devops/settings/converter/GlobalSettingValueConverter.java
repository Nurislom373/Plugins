package uz.devops.settings.converter;

public interface GlobalSettingValueConverter {

    String serializeValue(Object value) throws Exception;

    Object deserializeValue(String value) throws Exception;

}
