package com.dmdev.webStore.repository;

import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.entity.Order;

import java.util.List;

public interface FilterOrderRepository {

    //    List<Order> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter filter);
    List<Order> findAllOrdersWithProductsOfOneUser (PersonalInformationFilter filter);
}
