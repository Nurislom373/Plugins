package uz.devops.observability.service.collector;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 12:31 PM
 */
public interface ContextNameCollector {

    void add(String name);

    void remove(String name);

    boolean contains(String name);

    Set<String> getAll();

}
