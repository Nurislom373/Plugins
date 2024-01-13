package uz.devops.settings.converter;

/**
 * @author Nurislom
 * @see uz.devops.settings.converter
 * @since 11/23/2023 5:36 PM
 */
public interface GlobalSettingTypeValueConverter extends GlobalSettingValueConverter {

    default Class<? extends Enum> getType() {
        return null;
    }

}
