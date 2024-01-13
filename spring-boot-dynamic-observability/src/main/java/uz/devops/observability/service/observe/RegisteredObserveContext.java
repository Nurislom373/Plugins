package uz.devops.observability.service.observe;

import uz.devops.observability.enumeration.ObserveType;

import java.util.Optional;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.observe
 * @since 12/19/2023 6:34 PM
 */
public interface RegisteredObserveContext {

    Optional<RegisteredObserve> get(ObserveType type);

}
