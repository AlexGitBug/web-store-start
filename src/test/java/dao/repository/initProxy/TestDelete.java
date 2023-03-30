package dao.repository.initProxy;


import javax.persistence.EntityManager;

public abstract class TestDelete {

    private static final String CLEAN_SQL4 = "DELETE FROM catalog";
    private static final String CLEAN_SQL1 = "DELETE FROM orders";
    private static final String CLEAN_SQL2 = "DELETE FROM product";
    private static final String CLEAN_SQL = "DELETE FROM shopping_cart";
    private static final String CLEAN_SQL3 = "DELETE FROM users";

    public static void deleteAll(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(CLEAN_SQL).executeUpdate();
        entityManager.createNativeQuery(CLEAN_SQL1).executeUpdate();
        entityManager.createNativeQuery(CLEAN_SQL2).executeUpdate();
        entityManager.createNativeQuery(CLEAN_SQL3).executeUpdate();
        entityManager.createNativeQuery(CLEAN_SQL4).executeUpdate();

        entityManager.getTransaction().commit();
//        entityManager.close();
    }
}
