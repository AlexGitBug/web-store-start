package com.dmdev.webStore.repository;

import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.enums.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends
        JpaRepository<Order, Integer>,
        FilterOrderRepository{

    Optional<Order> findByUserId(Integer id);

    @Modifying
    @Query("update Order o " +
            "set o.status = :status " +
            "where o.id in (:id)")
    int setStatus(ProgressStatus status, Integer id);

    Optional<Order> findByStatusAndUserId(ProgressStatus status, Integer id);
    Optional<Order> findByIdAndStatus(Integer id, ProgressStatus status);

}