package uz.devops.settings.service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Nurislom
 * @see uz.devops.settings.service.configuration
 * @since 11/23/2023 4:39 PM
 */
public abstract class ConfigurationServiceUtils {

    public static final String LANG_KEY = "LangKey";
    public static final Locale DEFAULT = Locale.ENGLISH;
    public static final Locale RUSSIA = new Locale("ru");

    private static final Logger log = LoggerFactory.getLogger(ConfigurationServiceUtils.class);

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }

    public static Locale getCurrentRequestLocal() {
        Optional<HttpServletRequest> currentHttpRequest = getCurrentHttpRequest();
        if (currentHttpRequest.isPresent()) {
            return getLocalWithRequest(currentHttpRequest.get());
        } else {
            return RUSSIA;
        }
    }

    public static String getCurrentLanguage() {
        return getCurrentRequestLocal().getLanguage();
    }

    public static Class<?> springClassLoader(String name, ApplicationContext applicationContext) {
        try {
            ClassLoader contextClassLoader = applicationContext.getClassLoader();
            if (Objects.nonNull(contextClassLoader)) {
                return contextClassLoader.loadClass(name);
            }
        } catch (ClassNotFoundException e) {
            log.warn("class not found : {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> classLoader(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static Locale getLocalWithRequest(HttpServletRequest request) {
        String value = request.getHeader(LANG_KEY);
        return Objects.nonNull(value) ? new Locale(value) : DEFAULT;
    }

}
