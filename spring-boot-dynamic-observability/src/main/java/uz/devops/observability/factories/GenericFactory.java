package uz.devops.observability.factories;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 4:56 PM
 */
public interface GenericFactory<P, R> {

    R create(P p);

}
