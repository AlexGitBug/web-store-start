package dao.repository;

import entity.Catalog;

import javax.persistence.EntityManager;

public class CatalogRepository extends RepositoryBase<Integer, Catalog> {

    public CatalogRepository(Class<Catalog> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }


}
