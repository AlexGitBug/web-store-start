package dao;



import entity.BaseEntity;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.xml.catalog.Catalog;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {

    private final Class<E> clazz;
    private final EntityManager entityManager;

    @Override
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.flush();
    }

    @Override
    public boolean update(E entity) {
        entityManager.merge(entity);
        entityManager.flush();
        return true;
    }

    @Override
    public Optional<E> findById(K id, Map<String, Object> properties) {
        return Optional.ofNullable(entityManager.find(clazz, id, properties));
    }

    @Override
    public List<E> findAll() {
        var criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return entityManager.createQuery(criteria)
                .getResultList();
    }
}