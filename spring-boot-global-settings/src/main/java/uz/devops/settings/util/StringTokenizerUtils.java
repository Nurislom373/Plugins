package uz.devops.settings.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Supplier;

/**
 * @author Nurislom
 * @see uz.devops.settings.util
 * @since 11/22/2023 12:05 PM
 */
public abstract class StringTokenizerUtils {

    public static List<String> getTokenWithList(String str, String delim) {
        return getTokensWithGeneric(str, delim, false, ArrayList::new);
    }

    public static List<String> getTokensWithList(String str, String delim, boolean returnDelim) {
        return getTokensWithGeneric(str, delim, returnDelim, ArrayList::new);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected static  <T extends Collection> T getTokensWithGeneric(String str, String delim, boolean returnDelim,
                                                                    Supplier<T> collectionFactory) {
        T objects = collectionFactory.get();
        StringTokenizer token = new StringTokenizer(str, delim, returnDelim);
        while (token.hasMoreElements()) {
            objects.add(token.nextToken());
        }
        return objects;
    }

}
