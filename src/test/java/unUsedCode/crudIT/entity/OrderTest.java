package unUsedCode.crudIT.entity;

import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import com.dmdev.webStore.entity.enums.Role;
import org.junit.jupiter.api.Test;
import unUsedCode.dao.util.HibernateUtil;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    @Test
    void check() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var order = session.get(Order.class, 1);
            System.out.println(order);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkShoppingCartManyToMany() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(getUser());
            session.save(getProduct());

            var user = session.get(User.class, 1);
            var product = session.get(Product.class, 1);

            var order = Order.builder()
                    .deliveryAdress(DeliveryAdress.builder()
                            .city("Minsk")
                            .street("Pobeda")
                            .building(1)
                            .build())
                    .deliveryDate(LocalDate.of(2022, 2, 25))
                    .paymentCondition(PaymentCondition.CASH)
                    .build();
            order.setUser(user);
//            order.addProduct(product);


            session.save(order);


            session.getTransaction().commit();
        }
    }


    @Test
    void checkOrderSaveAndGet() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var order = getOrder();
            session.beginTransaction();
            session.save(order);

            session.getTransaction().commit();

            var actualResult = session.get(Order.class, order.getId());
            assertThat(actualResult).isEqualTo(order);

        }

    }

    private Order getOrder() {
        return Order.builder()
//                .userId(1)
//                .productId(3)
                .deliveryAdress(DeliveryAdress.builder()
                        .city("Minsk")
                        .street("Masherova")
                        .building(1)
                        .build())
                .deliveryDate(LocalDate.of(2022, 2, 25))
                .paymentCondition(PaymentCondition.CARD)
                .build();
    }

    private User getUser() {
        return User.builder()
                .personalInformation(PersonalInformation.builder()
                        .firstName("test")
                        .lastName("test")
                        .email("test")
                        .password("test")
                        .telephone("test")
                        .birthDate(LocalDate.of(2022, 1, 1))
                        .build())
                .role(Role.USER)
                .build();
    }

    private Product getProduct() {
        return Product.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.APPLE)
                .model("13")
                .dateOfRelease(LocalDate.of(2022, 1 , 1))
                .price(123)
                .color(Color.WHITE)
                .image("123")
                .build();
    }
}