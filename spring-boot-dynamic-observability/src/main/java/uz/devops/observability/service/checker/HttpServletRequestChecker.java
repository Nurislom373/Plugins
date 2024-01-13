package uz.devops.observability.service.checker;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:45 PM
 */
public interface HttpServletRequestChecker {

    boolean isRegistry(HttpServletRequest request);

}
