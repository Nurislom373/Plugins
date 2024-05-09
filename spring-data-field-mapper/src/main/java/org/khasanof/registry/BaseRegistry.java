package org.khasanof.registry;

import java.util.Collection;
import java.util.Set;

/**
 * @author Nurislom
 * @see org.khasanof.registry
 * @since 4/23/2024 6:39 PM
 */
public interface BaseRegistry<T> {

    Set<T> getAll();

    void add(T t);

    void addAll(Collection<T> collection);
}
