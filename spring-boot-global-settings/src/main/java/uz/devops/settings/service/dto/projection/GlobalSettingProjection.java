package uz.devops.settings.service.dto.projection;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.projection
 * @since 11/23/2023 5:02 PM
 */
public interface GlobalSettingProjection {

    Long getId();

    Long parentId();

    String getClassName();

    String getImplementClass();

    String getTitle();

}
