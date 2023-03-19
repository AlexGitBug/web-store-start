package dao.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Repository<K, T> {

    List<T> findAll();

    Optional<T> findById(K id, Map<String, Object> properties);

    default Optional<T> findById(K id) {
        return findById(id, emptyMap());
    }

    void delete(K id);

    T update(T entity);

    T save(T entity);
}