package uz.devops.observability.interceptor;

import io.micrometer.observation.Observation;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import uz.devops.observability.IObservationHandler;
import uz.devops.observability.factories.context.ObservationContextFactory;
import uz.devops.observability.factories.handler.ObservationHandlerFactory;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.service.checker.HttpServletRequestChecker;
import uz.devops.observability.service.registry.RestRegistryManager;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static uz.devops.observability.utils.GlobalConstants.CONTEXT_NAME;
import static uz.devops.observability.utils.GlobalConstants.METER_HANDLER_NAME;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:48 PM
 */
public abstract class AbstractRestInterceptor implements HandlerInterceptor {

    private final ObservationHandlerFactory observationHandlerFactory;
    private final HttpServletRequestChecker httpServletRequestChecker;
    private final ObservationContextFactory observationContextFactory;
    private final RestRegistryManager restRegistryManager;

    protected AbstractRestInterceptor(ObservationHandlerFactory observationHandlerFactory, HttpServletRequestChecker httpServletRequestChecker,
                                      ObservationContextFactory observationContextFactory, RestRegistryManager restRegistryManager) {
        this.observationHandlerFactory = observationHandlerFactory;
        this.httpServletRequestChecker = httpServletRequestChecker;
        this.observationContextFactory = observationContextFactory;
        this.restRegistryManager = restRegistryManager;
    }

    protected void observationStart(HttpServletRequest request) {
        if (httpServletRequestChecker.isRegistry(request)) {
            findResource(request);
        }
    }

    protected void observationStop() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Observation.Context context = (Observation.Context) requestAttributes.getAttribute(CONTEXT_NAME, RequestAttributes.SCOPE_REQUEST);
        IObservationHandler observationHandler = (IObservationHandler) requestAttributes.getAttribute(METER_HANDLER_NAME, RequestAttributes.SCOPE_REQUEST);
        observationStop(observationHandler, context);
    }

    private void findResource(HttpServletRequest request) {
        restRegistryManager.find(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()))
                .ifPresent(resource -> observationStart(request, resource));
    }

    private void observationStop(IObservationHandler observationHandler, Observation.Context context) {
        if (Objects.nonNull(observationHandler) && Objects.nonNull(context)) {
            observationHandler.onStop(context, true);
        }
    }

    private void observationStart(HttpServletRequest request, Resource resource) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        IObservationHandler observationHandler = observationHandlerFactory.create(resource);
        Observation.Context context = observationContextFactory.create(request.getRequestURI());
        requestAttributes.setAttribute(CONTEXT_NAME, context, RequestAttributes.SCOPE_REQUEST);
        requestAttributes.setAttribute(METER_HANDLER_NAME, observationHandler, RequestAttributes.SCOPE_REQUEST);
        observationHandler.onStart(context, true);
    }

}
