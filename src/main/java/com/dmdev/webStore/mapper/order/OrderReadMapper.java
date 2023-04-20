package com.dmdev.webStore.mapper.order;

import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.mapper.Mapper;
import com.dmdev.webStore.mapper.user.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper userReadMapper;


    @Override
    public OrderReadDto map(Order object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        return new OrderReadDto(
                object.getId(),
                object.getDeliveryAdress().getCity(),
                object.getDeliveryAdress().getStreet(),
                object.getDeliveryAdress().getBuilding(),
                object.getDeliveryDate(),
                object.getPaymentCondition(),
                user
        );
    }

    @Override
    public OrderReadDto map(Order fromObject, OrderReadDto toObject) {
        return Mapper.super.map(fromObject, toObject);
    }
}
