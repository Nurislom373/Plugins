package uz.devops.settings.factory.title;

import org.springframework.stereotype.Component;
import uz.devops.settings.domain.GlobalSettingTitle;
import uz.devops.settings.service.dto.configuration.Title;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nurislom
 * @see uz.devops.settings.factory.title
 * @since 12/21/2023 4:49 PM
 */
@Component
public class DefaultTitleFactory implements TitleFactory {

    @Override
    public List<Title> create(List<GlobalSettingTitle> titles) {
        if (titles.isEmpty()) {
            return Collections.emptyList();
        }
        return tryCreateTitles(titles);
    }

    private List<Title> tryCreateTitles(List<GlobalSettingTitle> titles) {
        return titles.stream().map(title -> Title.builder()
                .language(title.getLanguage()).title(title.getTitle())
                .build()).collect(Collectors.toList());
    }

}
