package uz.devops.observability.factories.handler;


import uz.devops.observability.IObservationHandler;
import uz.devops.observability.factories.GenericFactory;
import uz.devops.observability.models.resource.Resource;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:57 PM
 */
public interface ObservationHandlerFactory extends GenericFactory<Resource, IObservationHandler> {
}
