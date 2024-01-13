package uz.devops.observability.models.resource;

import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 6:20 PM
 */
public interface Resource {

    String getName(); // resource name must be unique!

    String getUri();

    RequestMethod getMethod();

    Set<ObserveType> getObserveTypes();

}
