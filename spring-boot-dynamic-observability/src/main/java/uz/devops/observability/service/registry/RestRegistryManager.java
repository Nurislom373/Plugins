package uz.devops.observability.service.registry;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.models.resource.Resource;

import java.util.Optional;
import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 6:20 PM
 */
public interface RestRegistryManager {

    void add(Resource resource);

    void addAll(Set<Resource> resources);

    void remove(Resource resource);

    void removeAll(Set<Resource> resources);

    void clear();

    Optional<Resource> find(String name);

    Optional<Resource> find(String uri, RequestMethod method);

    boolean contains(String name);

    boolean contains(String uri, RequestMethod method);

    Set<Resource> findAll();
}
