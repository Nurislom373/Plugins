package uz.devops.observability.factories.resource;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 4:56 PM
 */
public interface ResourceAbstractFactory {

    ResourceFactory resourceFactory();

    MetricsResourceFactory metricsResourceFactory();

}
