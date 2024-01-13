package uz.devops.observability.service.checker;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.service.registry.RestRegistryManager;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:45 PM
 */
@Service
public class DefaultHttpServletRequestChecker implements HttpServletRequestChecker {

    private final RestRegistryManager registryManager;

    public DefaultHttpServletRequestChecker(RestRegistryManager registryManager) {
        this.registryManager = registryManager;
    }

    @Override
    public boolean isRegistry(HttpServletRequest request) {
        return tryIsRegistry(request);
    }

    private boolean tryIsRegistry(HttpServletRequest request) {
        return registryManager.contains(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
    }

}
