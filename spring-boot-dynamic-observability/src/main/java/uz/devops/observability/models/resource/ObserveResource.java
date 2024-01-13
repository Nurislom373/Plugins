package uz.devops.observability.models.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.devops.observability.enumeration.ObserveType;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:24 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObserveResource {

    private String name;
    private HttpServletRequest request;
    private Set<ObserveType> observeTypes;

}
