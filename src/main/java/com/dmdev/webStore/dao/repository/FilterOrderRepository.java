package com.dmdev.webStore.dao.repository;

import com.dmdev.webStore.dao.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.dao.repository.filter.UserFilter;
import com.dmdev.webStore.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface FilterOrderRepository {

    List<Order> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter personalInformationFilter);
}
