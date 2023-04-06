package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends
        JpaRepository<Order, Integer>,
        FilterOrderRepository{

}
