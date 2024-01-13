package uz.devops.settings.factory;

/**
 * @author Nurislom
 * @see uz.devops.settings
 * @since 12/15/2023 4:56 PM
 */
public interface GenericFactory<P, R> {

    R create(P p);

}
