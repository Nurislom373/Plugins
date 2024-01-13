package uz.devops.observability.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 5:59 PM
 */
@Getter
@RequiredArgsConstructor
public enum ObserveType {

    TIMER("Timer intended to track of a large number of short running events. Example would be something like an HTTP request. Though \"short running\" is a bit subjective the assumption is that it should be under a minute.", true),
    LAST_REQUEST("LastRequest is designed to monitor how long the last HTTP request took to complete.", false),
    COUNTER("Counters monitor monotonically increasing values. Counters may never be reset to a lesser value. If you need to track a value that goes up and down", true);

    private final String description;
    private final boolean isRegistered;

}
