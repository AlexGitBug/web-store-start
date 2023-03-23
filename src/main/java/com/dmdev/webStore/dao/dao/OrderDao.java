package com.dmdev.webStore.dao.dao;

import com.dmdev.webStore.entity.Order;

import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Integer, Order> {

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Order update(Order entity) {
        return null;
    }

    @Override
    public Order save(Order entity) {
        return null;
    }
}
