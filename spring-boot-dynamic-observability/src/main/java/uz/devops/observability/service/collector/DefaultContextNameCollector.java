package uz.devops.observability.service.collector;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 12:32 PM
 */
@Component
public class DefaultContextNameCollector implements ContextNameCollector {

    private final Set<String> contextNames = new HashSet<>();

    @Override
    public void add(String name) {
        this.contextNames.add(name);
    }

    @Override
    public void remove(String name) {
        this.contextNames.remove(name);
    }

    @Override
    public boolean contains(String name) {
        return this.contextNames.contains(name);
    }

    @Override
    public Set<String> getAll() {
        return this.contextNames;
    }

}
