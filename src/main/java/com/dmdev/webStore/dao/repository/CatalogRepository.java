package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.Catalog;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
@Repository
public class CatalogRepository extends RepositoryBase<Integer, Catalog> {

    public CatalogRepository(Class<Catalog> clazz,
                             EntityManager entityManager) {
        super(clazz, entityManager);

    }

}
