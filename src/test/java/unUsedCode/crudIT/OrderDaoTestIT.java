package unUsedCode.crudIT;

import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static unUsedCode.dao.util.SessionUtil.closeTransactionSession;
import static unUsedCode.dao.util.SessionUtil.openTransactionSession;

class OrderDaoTestIT {

    @Test
    void findAll() {
        try (Session session = openTransactionSession()) {
            var order = getOrder();
            var orderTest = getOrderTest();
            session.save(order);
            session.save(orderTest);
            closeTransactionSession();

            String sql = """
                    SELECT *
                    FROM orders
                    """;
            var query = session.createSQLQuery(sql).addEntity(Order.class);
            var actualResult = query.list();

            assertThat(actualResult).containsExactlyInAnyOrder(order, orderTest);
        }
    }

    @Test
    void findById() {
        try (Session session = openTransactionSession()) {
            var order = getOrder();
            session.save(order);
            closeTransactionSession();

            var actualResult = session.get(Order.class, order.getId());

            assertThat(actualResult).isEqualTo(order);
        }
    }

    @Test
    void delete() {
        try (Session session = openTransactionSession()) {
            var order = getOrder();
            session.save(order);
            closeTransactionSession();

            session.delete(order);

            var actualResult = session.get(Order.class, order.getId());
            assertThat(actualResult).isNull();
        }
    }

    @Test
    void update() {
        try (Session session = openTransactionSession()) {
            var order = getOrder();
            session.save(order);
            closeTransactionSession();

            var updatedOrder = session.get(Order.class, order.getId());
            updatedOrder.setPaymentCondition(PaymentCondition.CARD);
            session.update(order);

            var actualResult = session.get(Order.class, order.getId());
            assertThat(actualResult).isEqualTo(order);
        }
    }

    @Test
    void save() {
        try (Session session = openTransactionSession()) {
            var order = getOrder();

            session.save(order);
            closeTransactionSession();

            var actualResult = session.get(Order.class, order.getId());
            assertNotNull(actualResult.getId());
        }
    }

    private static Order getOrder() {
        return Order.builder()
                .deliveryAdress(DeliveryAdress.builder()
                        .city("Test")
                        .street("Test")
                        .building(1)
                        .build())
                .deliveryDate(LocalDate.of(2023, 3, 5))
                .paymentCondition(PaymentCondition.CASH)
                .build();
    }

    private static Order getOrderTest() {
        return Order.builder()
                .deliveryAdress(DeliveryAdress.builder()
                        .city("Test1")
                        .street("Test1")
                        .building(2)
                        .build())
                .deliveryDate(LocalDate.of(2023, 3, 5))
                .paymentCondition(PaymentCondition.CARD)
                .build();
    }
}