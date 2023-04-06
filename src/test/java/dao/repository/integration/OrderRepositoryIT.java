package dao.repository.integration;

import com.dmdev.webStore.dao.repository.OrderRepository;
import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.dao.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import dao.repository.integration.annotation.IT;
import dao.repository.util.TestDataImporter;
import dao.repository.util.TestDelete;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@IT
@RequiredArgsConstructor
public class OrderRepositoryIT {
    private static final String MAIL_PETR = "petr@gmail.com";
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void deleteAllData() {
        TestDelete.deleteAll(entityManager);
    }

    @Test
    void deleteOrderIT() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

        orderRepository.delete(order);

        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    void saveOrderIT() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);

        orderRepository.save(order);

        assertThat(order.getId()).isNotNull();
    }

    @Test
    void updateOderIT() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

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
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

        var actualResult = orderRepository.findById(order.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(order);
    }

    @Test
    void findAllOrderIT() {
        orderRepository.save(Order.builder()
                .paymentCondition(PaymentCondition.CARD).build());
        orderRepository.save(Order.builder()
                .paymentCondition(PaymentCondition.CASH).build());

        var actualResult = orderRepository.findAll();

        List<String> orders = actualResult.stream()
                .map(Order::getPaymentCondition)
                .map(Enum::name)
                .toList();
        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(orders).containsExactlyInAnyOrder(
                        PaymentCondition.CARD.name(), PaymentCondition.CASH.name()
                )
        );
    }

    @Test
    void findAllOrdersWithProductsOfOneUserIT() {
        TestDataImporter.importData(entityManager);

        var filter = PersonalInformationFilter.builder()
                .email(MAIL_PETR)
                .build();

        var results = orderRepository.findAllOrdersWithProductsOfOneUser(filter);

        var orderId = results.stream().map(Order::getId).collect(toList());

        assertAll(
                () -> assertThat(results).hasSize(3),
                () -> assertThat(orderId).containsExactlyInAnyOrder(
                        9, 9, 9
                )
        );
    }

}
