package uz.devops.settings.factory.title;

import uz.devops.settings.domain.GlobalSettingTitle;
import uz.devops.settings.factory.GenericFactory;
import uz.devops.settings.service.dto.configuration.Title;

import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.title
 * @since 12/21/2023 4:49 PM
 */
public interface TitleFactory extends GenericFactory<List<GlobalSettingTitle>, List<Title>> {
}
