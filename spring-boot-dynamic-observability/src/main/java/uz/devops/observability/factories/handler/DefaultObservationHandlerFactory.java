package uz.devops.observability.factories.handler;

import org.springframework.stereotype.Component;
import uz.devops.observability.interceptor.AbstractObservationHandler;
import uz.devops.observability.IObservationHandler;
import uz.devops.observability.context.ResourceContext;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.utils.GlobalConstants;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/16/2023 11:50 AM
 */
@Component
public class DefaultObservationHandlerFactory implements ObservationHandlerFactory {

    private final ResourceContext context;

    public DefaultObservationHandlerFactory(ResourceContext context) {
        this.context = context;
    }

    @Override
    public IObservationHandler create(Resource resource) {
        return createHandler(resource.getObserveTypes());
    }

    private IObservationHandler createHandler(Set<ObserveType> types) {
        Iterator<ObserveType> iterator = types.iterator();
        AbstractObservationHandler observationHandler = (AbstractObservationHandler) context.get(iterator.next());
        setNextHandlers(iterator, observationHandler);
        return observationHandler;
    }

    private void setNextHandlers(Iterator<ObserveType> iterator, AbstractObservationHandler nextHandler) {
        while (iterator.hasNext()) {
            AbstractObservationHandler handler = (AbstractObservationHandler) context.get(iterator.next());
            nextHandler.next(handler);
            nextHandler = handler;
        }
    }

}
