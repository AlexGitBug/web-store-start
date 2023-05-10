package com.dmdev.webStore.mapper.shoppingCart;

import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartReadDto;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.mapper.Mapper;
import com.dmdev.webStore.mapper.order.OrderReadMapper;
import com.dmdev.webStore.mapper.product.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ShoppingCartReadMapper implements Mapper<ShoppingCart, ShoppingCartReadDto> {

    private final OrderReadMapper orderReadMapper;

    private final ProductReadMapper productReadMapper;

    @Override
    public ShoppingCartReadDto map(ShoppingCart object) {
        OrderReadDto order = Optional.ofNullable(object.getOrder())
                .map(orderReadMapper::map)
                .orElse(null);

        ProductReadDto product = Optional.ofNullable(object.getProduct())
                .map(productReadMapper::map)
                .orElse(null);

        return new ShoppingCartReadDto(
                object.getId(),
                order,
                product,
                object.getCreatedAt(),
                object.getCount()
        );
    }
}

