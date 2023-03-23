package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.Catalog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CatalogRepository extends RepositoryBase<Integer, Catalog> {
    public CatalogRepository(EntityManager entityManager) {
        super(Catalog.class, entityManager);
    }
}
