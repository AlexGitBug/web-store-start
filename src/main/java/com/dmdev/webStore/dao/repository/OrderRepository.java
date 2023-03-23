package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@Repository
public class OrderRepository extends  RepositoryBase<Integer, Order>{
    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
