package com.dmdev.webStore.mapper.shoppingCart;

import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.mapper.Mapper;
import com.dmdev.webStore.repository.OrderRepository;
import com.dmdev.webStore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShoppingCartCreateEditMapper implements Mapper<ShoppingCartCreateEditDto, ShoppingCart> {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public ShoppingCart map(ShoppingCartCreateEditDto object) {
        ShoppingCart shoppingCart = new ShoppingCart();
        copy(object, shoppingCart);

        return shoppingCart;
    }

    private void copy(ShoppingCartCreateEditDto object, ShoppingCart shoppingCart) {
        shoppingCart.setProduct(getProduct(object.getOrderId()));
        shoppingCart.setOrder(getOrder(object.getOrderId()));
        shoppingCart.setCreatedAt(object.getCreatedAt());
    }

    private Order getOrder(Integer orderId) {
        return Optional.ofNullable(orderId)
                .flatMap(id -> orderRepository.findById(id))
                .orElse(null);
    }

    private Product getProduct(Integer productId) {
        return Optional.ofNullable(productId)
                .flatMap(id -> productRepository.findById(id))
                .orElse(null);
    }
}
