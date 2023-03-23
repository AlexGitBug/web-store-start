package com.dmdev.webStore.service;

import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.ShoppingCart;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Arrays;

public class ShoppingCartService {

    private void addProduct(Session session, Order order, Product... products) {
        Arrays.stream(products)
                .map(product -> ShoppingCart.builder()
                        .order(order)
                        .product(product)
                        .createdAt(LocalDate.now())
                        .build())
                .forEach(session::save);
    }
}
