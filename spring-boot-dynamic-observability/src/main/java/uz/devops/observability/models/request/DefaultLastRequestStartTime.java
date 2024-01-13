package uz.devops.observability.models.request;

import lombok.*;

import java.time.Instant;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 4:12 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefaultLastRequestStartTime implements LastRequestStartTime {

    private Instant startTime;

}
