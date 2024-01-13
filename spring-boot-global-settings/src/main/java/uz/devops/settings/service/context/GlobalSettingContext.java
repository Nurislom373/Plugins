package uz.devops.settings.service.context;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.context
 * @since 11/25/2023 5:01 PM
 */
public interface GlobalSettingContext {

    /**
     * searches the database according to the parameter you entered, and if there is a database, it creates an instance
     * of this class and fills it with the values from the database and returns it to you, otherwise it returns {@code null}.
     *
     * @param configClass You must include the GlobalSetting annotated class otherwise it will return {@code null}
     * @return If the class you existed is in the database, it will take all the values from the database and create the
     *         class you provided and fill it in.
     * @since 0.1.12
     */
    <T> T getConfiguration(Class<T> configClass);

    /**
     *
     *
     * @param configClass You must include the GlobalSetting annotated class otherwise it will return {@code null}
     * @return configuration new instance
     * @since 0.1.17
     */
    <T> T newConfiguration(Class<T> configClass);

}
