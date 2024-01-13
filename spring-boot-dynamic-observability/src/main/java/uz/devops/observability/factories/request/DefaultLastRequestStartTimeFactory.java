package uz.devops.observability.factories.request;

import org.springframework.stereotype.Component;
import uz.devops.observability.models.request.DefaultLastRequestStartTime;
import uz.devops.observability.models.request.LastRequestStartTime;

import java.time.Instant;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 2:42 PM
 */
@Component
public class DefaultLastRequestStartTimeFactory implements LastRequestStartTimeFactory {

    @Override
    public LastRequestStartTime create(Instant startTime) {
        return new DefaultLastRequestStartTime(startTime);
    }

}
