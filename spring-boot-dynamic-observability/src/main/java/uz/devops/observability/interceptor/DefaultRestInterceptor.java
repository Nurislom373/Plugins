package uz.devops.observability.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import uz.devops.observability.factories.context.ObservationContextFactory;
import uz.devops.observability.factories.handler.ObservationHandlerFactory;
import uz.devops.observability.service.checker.HttpServletRequestChecker;
import uz.devops.observability.service.registry.RestRegistryManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 3:31 PM
 */
@Slf4j
@Component
public class DefaultRestInterceptor extends AbstractRestInterceptor {

    protected DefaultRestInterceptor(ObservationHandlerFactory observationHandlerFactory, HttpServletRequestChecker httpServletRequestChecker,
                                     ObservationContextFactory observationContextFactory, RestRegistryManager restRegistryManager) {
        super(observationHandlerFactory, httpServletRequestChecker, observationContextFactory, restRegistryManager);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        observationStart(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        observationStop();
    }

}
