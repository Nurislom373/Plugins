package uz.devops.observability.factories.request;


import uz.devops.observability.models.request.LastRequestStartTime;

import java.time.Instant;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 2:42 PM
 */
public interface LastRequestStartTimeFactory {

    LastRequestStartTime create(Instant startTime);

}
