package uz.devops.observability.factories.resource;

import org.springframework.stereotype.Component;
import uz.devops.observability.factories.observe.ObserveMetricNameFactory;
import uz.devops.observability.models.resource.DefaultMetricsResource;
import uz.devops.observability.models.resource.DefaultResource;
import uz.devops.observability.models.resource.Resource;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability.factories.resource
 * @since 12/19/2023 6:23 PM
 */
@Component
public class DefaultMetricsResourceFactory implements MetricsResourceFactory {

    private final ObserveMetricNameFactory observeMetricNameFactory;

    public DefaultMetricsResourceFactory(ObserveMetricNameFactory observeMetricNameFactory) {
        this.observeMetricNameFactory = observeMetricNameFactory;
    }

    @Override
    public Resource create(DefaultResource defaultResource) {
        DefaultMetricsResource metricsResource = getMetricsResource(defaultResource);
        fillResourceOtherFields(defaultResource, metricsResource);
        return metricsResource;
    }

    private DefaultMetricsResource getMetricsResource(DefaultResource defaultResource) {
        DefaultMetricsResource resource = new DefaultMetricsResource();
        resource.setMetricsName(getMetricsName(defaultResource));
        return resource;
    }

    private void fillResourceOtherFields(DefaultResource defaultResource, DefaultMetricsResource metricsResource) {
        metricsResource.setObserveTypes(defaultResource.getObserveTypes());
        metricsResource.setMethod(defaultResource.getMethod());
        metricsResource.setName(defaultResource.getName());
        metricsResource.setUri(defaultResource.getUri());
    }

    private Set<String> getMetricsName(DefaultResource defaultResource) {
        return observeMetricNameFactory.createMetricsName(defaultResource.getUri(), defaultResource.getObserveTypes());
    }

}
