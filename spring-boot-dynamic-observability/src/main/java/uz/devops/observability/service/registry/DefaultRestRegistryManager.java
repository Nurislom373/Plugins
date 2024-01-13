package uz.devops.observability.service.registry;


import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.models.resource.Resource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/14/2023 6:25 PM
 */
@Component
public class DefaultRestRegistryManager implements RestRegistryManager {

    private final Set<Resource> resources = new HashSet<>();

    @Override
    public void add(Resource resource) {
        resources.add(resource);
    }

    @Override
    public void addAll(Set<Resource> resources) {
        if (!resources.isEmpty()) {
            this.resources.addAll(resources);
        }
    }

    @Override
    public void remove(Resource resource) {
        resources.remove(resource);
    }

    @Override
    public void removeAll(Set<Resource> resources) {
        if (!resources.isEmpty()) {
            this.resources.removeAll(resources);
        }
    }

    @Override
    public void clear() {
        resources.clear();
    }

    @Override
    public Optional<Resource> find(String name) {
        return findBy(name);
    }

    @Override
    public Optional<Resource> find(String uri, RequestMethod method) {
        return findBy(uri, method);
    }

    @Override
    public boolean contains(String name) {
        return findBy(name).isPresent();
    }

    @Override
    public boolean contains(String uri, RequestMethod method) {
        return findBy(uri, method).isPresent();
    }

    @Override
    public Set<Resource> findAll() {
        return this.resources;
    }

    public Optional<Resource> findBy(Predicate<Resource> predicate) {
        return this.resources.stream().filter(predicate).findFirst();
    }

    private Optional<Resource> findBy(String name) {
        return findBy(resource -> equals(name, resource));
    }

    private Optional<Resource> findBy(String uri, RequestMethod method) {
        return findBy(resource -> equals(uri, method, resource));
    }

    private boolean equals(String uri, RequestMethod method, Resource resource) {
        return Objects.equals(uri, resource.getUri()) && Objects.equals(method, resource.getMethod());
    }

    private boolean equals(String name, Resource resource) {
        return Objects.equals(name, resource.getName());
    }

}
