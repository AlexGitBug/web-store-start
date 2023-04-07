package dao.repository.util;

import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import com.dmdev.webStore.entity.enums.Role;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class MocksForRepository {

    public static Product getProduct(Catalog catalog) {
        return Product.builder()
                .catalog(catalog)
                .brand(Brand.SONY)
                .model("test")
                .dateOfRelease(LocalDate.of(2021, 1, 1))
                .price(100)
                .color(Color.BLACK)
                .image("test")
                .build();
    }

    public static Catalog getCatalog() {
        return Catalog.builder()
                .category("test")
                .build();
    }

    public static Order getOrder(User user) {
        return Order.builder()
                .deliveryAdress(DeliveryAdress.builder()
                        .city("test")
                        .street("test")
                        .building(1000)
                        .build())
                .deliveryDate(LocalDate.of(1111, 11, 1))
                .paymentCondition(PaymentCondition.CASH)
                .user(user)
                .build();
    }

    public static User getUser() {
        return User.builder()
                .personalInformation(PersonalInformation.builder()
                        .firstName("test")
                        .lastName("test")
                        .telephone("test")
                        .email("test@gmail.com")
                        .birthDate(LocalDate.of(1111, 11, 1))
                        .build())
                .role(Role.USER)
                .build();
    }

    public static ShoppingCart shoppingCart(Order order,Product product) {
        return ShoppingCart.builder()
                .createdAt(LocalDate.of(2018, 12, 1))
                .product(product)
                .order(order)
                .build();
    }

}

