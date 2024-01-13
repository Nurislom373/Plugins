package uz.devops.observability.service.convention;

import org.springframework.stereotype.Service;
import uz.devops.observability.enumeration.ObserveType;

import static uz.devops.observability.utils.BaseUtils.concat;
import static uz.devops.observability.utils.MeterSuffixes.getConvention;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.convention
 * @since 12/19/2023 5:52 PM
 */
@Service
public class PromethuesNamingConventions implements NamingConventions {

    @Override
    public String convention(String name) {
        return replaceURISlashes(name);
    }

    @Override
    public String convention(String name, ObserveType type) {
        return concat(replaceURISlashes(name), getConvention(type));
    }

    private String replaceURISlashes(String name) {
        String newName = name;
        if (name.startsWith("/")) {
            newName = name.replaceFirst("/", "");
        }
        return newName.replaceAll("/", "_");
    }

}
