package uz.devops.settings.service.dto.configuration;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.dto.configuration
 * @since 12/21/2023 4:30 PM
 */
public interface Configuration {

    Long getId();

    String getName();

    List<Title> getTitles();

}
