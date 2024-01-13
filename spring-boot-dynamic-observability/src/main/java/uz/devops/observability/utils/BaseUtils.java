package uz.devops.observability.utils;

import io.micrometer.common.KeyValue;
import io.micrometer.core.instrument.Tag;
import io.micrometer.observation.Observation;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 3:02 PM
 */
public abstract class BaseUtils {

    public static HttpMethod asHttpMethod(RequestMethod method) {
        switch (method) {
            case GET:
                return HttpMethod.GET;
            case HEAD:
                return HttpMethod.HEAD;
            case POST:
                return HttpMethod.POST;
            case PUT:
                return HttpMethod.PUT;
            case PATCH:
                return HttpMethod.PATCH;
            case DELETE:
                return HttpMethod.DELETE;
            case OPTIONS:
                return HttpMethod.OPTIONS;
            case TRACE:
                return HttpMethod.TRACE;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String concat(String var1, String var2) {
        return var1.concat(var2);
    }

    public static long betweenMillisSeconds(Instant startTime, Instant endTime) {
        return Duration.between(startTime, endTime).toMillis();
    }

    public static double millisSecondsToDouble(long millis) {
        return millis / 1000.0;
    }

    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().anyMatch(predicate);
    }

    public static String getErrorValue(Observation.Context context) {
        Throwable error = context.getError();
        return error != null ? error.getClass().getSimpleName() : "none";
    }

    public static List<Tag> createTags(Observation.Context context) {
        List<Tag> tags = new ArrayList<>();
        for (KeyValue keyValue : context.getLowCardinalityKeyValues()) {
            tags.add(Tag.of(keyValue.getKey(), keyValue.getValue()));
        }
        return tags;
    }

}
