package uz.devops.observability.service.convention;

import uz.devops.observability.enumeration.ObserveType;

/**
 * @author Nurislom
 * @see uz.devops.observability.service.convention
 * @since 12/19/2023 5:50 PM
 */
public interface NamingConventions {

    String convention(String name);

    String convention(String name, ObserveType type);

}
