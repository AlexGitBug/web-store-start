package dao.repository.integration;

import com.dmdev.webStore.dao.repository.OrderRepository;
import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.dao.repository.filter.UserFilter;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import dao.repository.integration.annotation.IT;
import dao.repository.util.TestDataImporter;
import dao.repository.util.TestDelete;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import javax.persistence.EntityManager;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(orderRepository.findById(order.getId())).contains(order);
    }

    @Test
    void updateOderIT() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

        var updatedOrder = orderRepository.findById(order.getId());
        updatedOrder.ifPresent(it -> it.setPaymentCondition(PaymentCondition.CARD));
        orderRepository.update(updatedOrder.get());

        var actualResult = orderRepository.findById(order.getId());
        assertThat(actualResult).contains(order);
    }

    @Test
    void findByIdOrderIT() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

        var actualResult = orderRepository.findById(order.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult).contains(order);

    }

    @Test
    void findAllOrdersWithProductsOfOneUserIT() {
        TestDataImporter.importData(entityManager);

        var userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .email(MAIL_PETR)
                        .build())
                .build();

        var results = orderRepository.findAllOrdersWithProductsOfOneUser(userFilter);
        assertThat(results).hasSize(3);

        var orderId = results.stream().map(Order::getId).collect(toList());
        assertThat(orderId).contains(7, 7, 7);
    }

}
