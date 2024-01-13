package uz.devops.observability.factories.resource;

import org.springframework.stereotype.Service;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:32 PM
 */
@Service
public class DefaultResourceAbstractFactory implements ResourceAbstractFactory {

    private final DefaultResourceFactory defaultResourceFactory;
    private final DefaultMetricsResourceFactory defaultMetricsResourceFactory;

    public DefaultResourceAbstractFactory(DefaultResourceFactory defaultResourceFactory, DefaultMetricsResourceFactory metricsResourceFactory) {
        this.defaultResourceFactory = defaultResourceFactory;
        this.defaultMetricsResourceFactory = metricsResourceFactory;
    }

    @Override
    public ResourceFactory resourceFactory() {
        return this.defaultResourceFactory;
    }

    @Override
    public MetricsResourceFactory metricsResourceFactory() {
        return this.defaultMetricsResourceFactory;
    }

}
