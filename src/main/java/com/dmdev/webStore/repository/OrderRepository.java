package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends
        JpaRepository<Order, Integer>,
        FilterOrderRepository{

    Optional<Order> findByUserId(Integer id);

}
