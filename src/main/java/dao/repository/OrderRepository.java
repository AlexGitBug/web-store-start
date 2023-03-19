package dao.repository;

import entity.Order;

import javax.persistence.EntityManager;

public class OrderRepository extends  RepositoryBase<Integer, Order>{

    public OrderRepository(Class<Order> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
