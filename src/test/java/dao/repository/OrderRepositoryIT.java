package dao.repository;

import com.dmdev.webStore.dao.repository.OrderRepository;
import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.dao.repository.filter.UserFilter;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import dao.repository.initProxy.ProxySessionTestBase;
import dao.repository.util.TestDataImporter;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderRepositoryIT extends ProxySessionTestBase {

    private final OrderRepository orderRepository = applicationContext.getBean(OrderRepository.class);
    private final UserRepository userRepository = applicationContext.getBean(UserRepository.class);

    @Test
    void deleteOrder() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

        orderRepository.delete(order);

        var actualResult = entityManager.find(Order.class, order.getId());
        assertThat(actualResult).isNull();
        entityManager.getTransaction().commit();
    }

    @Test
    void saveOrder() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);

        orderRepository.save(order);

        var actualResult = entityManager.find(Order.class, order.getId());
        assertThat(actualResult.getId()).isEqualTo(order.getId());
        entityManager.getTransaction().commit();
    }

    @Test
    void updateOder() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);


        var result = entityManager.find(Order.class, order.getId());
        order.setPaymentCondition(PaymentCondition.CARD);
        orderRepository.update(result);

        var actualResult = entityManager.find(Order.class, result.getId());
        assertThat(actualResult).isEqualTo(order);
        entityManager.getTransaction().commit();
    }

    @Test
    void findByIdOrder() {
        entityManager.getTransaction().begin();
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);

        var actualResult = entityManager.find(Order.class, order.getId());

        assertThat(actualResult).isEqualTo(order);
        entityManager.getTransaction().commit();
    }

    @Test
    void findAllOrdersWithProductsOfOneUser() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var userFilter = UserFilter.builder()
                .personalInformation(PersonalInformation.builder()
                        .email("petr@gmail.com")
                        .build())
                .build();

        var results = orderRepository.findAllOrdersWithProductsOfOneUser(userFilter);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(3);

        var orderId = results.stream().map(Order::getId).collect(toList());
        assertThat(orderId).contains(7,7,7);
    }

}
