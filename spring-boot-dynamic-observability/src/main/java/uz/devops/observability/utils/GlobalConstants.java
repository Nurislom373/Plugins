package uz.devops.observability.utils;

import uz.devops.observability.enumeration.ObserveType;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 6:02 PM
 */
public abstract class GlobalConstants {

    public static final Set<ObserveType> DEFAULT_OBSERVE_TYPES = Set.of(ObserveType.TIMER);
    public static final String CONTEXT_NAME = "context";
    public static final String METER_HANDLER_NAME = "meter_handler";

}
