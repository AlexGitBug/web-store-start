package dao.repository;

import entity.Order;
import entity.User;
import entity.enums.PaymentCondition;
import org.junit.jupiter.api.Test;
import util.TestCreateObjectForRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryIT extends ProductRepositoryIT {

    private final OrderRepository orderRepository = new OrderRepository(Order.class, session);
    private final UserRepository userRepository = new UserRepository(User.class, session);

    @Test
    void deleteOrder() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);

        orderRepository.delete(order.getId());

        var actualResult = session.get(Order.class, order.getId());
        assertThat(actualResult).isNull();
    }

    @Test
    void saveOrder() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);

        orderRepository.save(order);

        var actualResult = session.get(Order.class, order.getId());
        assertThat(actualResult.getId()).isEqualTo(order.getId());
    }

    @Test
    void updateOder() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);


        var result = session.get(Order.class, order.getId());
        order.setPaymentCondition(PaymentCondition.CARD);
        orderRepository.update(result);

        var actualResult = session.get(Order.class, result.getId());
        assertThat(actualResult).isEqualTo(order);
    }

    @Test
    void findByIdOrder() {
        var user = TestCreateObjectForRepository.getUser();
        userRepository.save(user);
        var order = TestCreateObjectForRepository.getOrder(user);
        orderRepository.save(order);

        var actualResult = session.get(Order.class, order.getId());

        assertThat(actualResult).isEqualTo(order);
    }
}
