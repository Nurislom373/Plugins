package uz.devops.observability;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import uz.devops.observability.enumeration.ObserveType;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/16/2023 11:41 AM
 */
public interface IObservationHandler extends ObservationHandler<Observation.Context> {

    void onStart(Observation.Context context, boolean moveToNext);

    void onStop(Observation.Context context, boolean moveToNext);

    ObserveType observeType();

    @Override
    default boolean supportsContext(Observation.Context context) {
        return true;
    }

}
