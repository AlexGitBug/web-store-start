package dao;

import entity.Catalog;
import query.Query;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

public class CatalogRepository extends RepositoryBase<Integer, Catalog> {

    public CatalogRepository(Class<Catalog> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }


}
