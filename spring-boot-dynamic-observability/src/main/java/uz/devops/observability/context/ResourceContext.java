package uz.devops.observability.context;

import uz.devops.observability.IObservationHandler;
import uz.devops.observability.enumeration.ObserveType;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/16/2023 11:58 AM
 */
public interface ResourceContext {

    IObservationHandler get(ObserveType key);

}
