package uz.devops.observability.utils;

import uz.devops.observability.enumeration.ObserveType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 3:47 PM
 */

public abstract class MeterSuffixes {

    private static final Map<ObserveType, String> OBSERVE_TYPE_MAP = new HashMap<>();
    public static final String TIMER = "_timer";
    public static final String COUNTER = "_counter";
    public static final String LAST_REQUEST = "_last_request";

    public static String getConvention(ObserveType type) {
        return OBSERVE_TYPE_MAP.get(type);
    }

    static {
        OBSERVE_TYPE_MAP.put(ObserveType.COUNTER, COUNTER);
        OBSERVE_TYPE_MAP.put(ObserveType.TIMER, TIMER);
        OBSERVE_TYPE_MAP.put(ObserveType.LAST_REQUEST, LAST_REQUEST);
    }

}
