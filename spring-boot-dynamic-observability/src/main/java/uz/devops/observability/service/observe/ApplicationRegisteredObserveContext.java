package uz.devops.observability.service.observe;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.observability.enumeration.ObserveType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.observe
 * @since 12/19/2023 6:35 PM
 */
@Service
public final class ApplicationRegisteredObserveContext implements RegisteredObserveContext, InitializingBean {

    private final ApplicationContext applicationContext;
    private final Map<ObserveType, RegisteredObserve> multiMetricsMap = new HashMap<>();

    public ApplicationRegisteredObserveContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Optional<RegisteredObserve> get(ObserveType type) {
        return Optional.ofNullable(multiMetricsMap.get(type));
    }

    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(RegisteredObserve.class)
                .forEach((beanName, bean) -> multiMetricsMap.put(bean.getType(), bean));
    }
}
