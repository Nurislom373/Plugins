package uz.devops.observability.context;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uz.devops.observability.IObservationHandler;
import uz.devops.observability.enumeration.ObserveType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/16/2023 11:51 AM
 */
@Component
public class DefaultResourceContext implements InitializingBean, ResourceContext {

    private final Map<ObserveType, IObservationHandler> handlers = new HashMap<>();
    private final ApplicationContext applicationContext;

    public DefaultResourceContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public IObservationHandler get(ObserveType key) {
        return handlers.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(IObservationHandler.class)
                .forEach((beanName, bean) -> handlers.put(bean.observeType(), bean));
    }
}
