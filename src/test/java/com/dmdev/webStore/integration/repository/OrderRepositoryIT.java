package com.dmdev.webStore.integration.repository;

import com.dmdev.webStore.integration.IntegrationTestBase;
import com.dmdev.webStore.repository.OrderRepository;
import com.dmdev.webStore.repository.UserRepository;
import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import com.dmdev.webStore.util.MocksForRepository;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@RequiredArgsConstructor
public class OrderRepositoryIT extends IntegrationTestBase {
    private static final String MAIL_PETR = "petr@gmail.com";
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Test
    void deleteOrderIT() {
        Order order = getOrder();

        orderRepository.delete(order);

        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    void saveOrderIT() {
        Order order = getOrder();

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void updateOderIT() {
        Order order = getOrder();

        var updatedOrder = orderRepository.findById(order.getId());
        updatedOrder.ifPresent(it -> it.setPaymentCondition(PaymentCondition.CARD));
        orderRepository.saveAndFlush(updatedOrder.get());

        var actualResult = orderRepository.findById(order.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getPaymentCondition())
                .isEqualTo(order.getPaymentCondition());
    }

    @Test
    void findByIdOrderIT() {
        Order order = getOrder();

        var actualResult = orderRepository.findById(order.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(order);
    }

    @Test
    void findAllOrderIT() {
        var actualResult = orderRepository.findAll();

        assertThat(actualResult).hasSize(8);

    }

//    @Test
//    void findAllOrdersWithProductsOfOneUserIT() {
//        var filter = PersonalInformationFilter.builder()
//                .email(MAIL_PETR)
//                .build();
//
//        var results = orderRepository.findAllOrdersWithProductsOfOneUser(filter);
//
//        var orderId = results.stream().map(Order::getId).collect(toList());
//
//        assertAll(
//                () -> assertThat(results).hasSize(3),
//                () -> assertThat(orderId).containsExactlyInAnyOrder(
//                        3, 3, 3
//                )
//        );
//    }

    private Order getOrder() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);
        return order;
    }

}
