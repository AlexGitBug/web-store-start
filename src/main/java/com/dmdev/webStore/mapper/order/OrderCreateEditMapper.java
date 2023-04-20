package com.dmdev.webStore.mapper.order;

import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.mapper.Mapper;
import com.dmdev.webStore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final UserRepository userRepository;

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setDeliveryAdress(new DeliveryAdress(
                object.getCity(),
                object.getStreet(),
                object.getBuilding()
        ));
        order.setDeliveryDate(object.getDeliveryDate());
        order.setPaymentCondition(object.getPaymentCondition());
        order.setUser(getUser(object.getUserId()));
    }

    private User getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
