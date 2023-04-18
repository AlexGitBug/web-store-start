package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends
        JpaRepository<Order, Integer>,
        FilterOrderRepository{

}
