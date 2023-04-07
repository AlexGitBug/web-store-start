package dao.repository.integration;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.dao.repository.OrderRepository;
import com.dmdev.webStore.dao.repository.ProductRepository;
import com.dmdev.webStore.dao.repository.ShoppingCartRepository;
import com.dmdev.webStore.dao.repository.UserRepository;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.entity.enums.Brand;

import lombok.RequiredArgsConstructor;;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;
import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
public class ShoppingCartRepositoryIT extends IntegrationTestBase{

    private final String MAIL_DIMA = "dima@gmail.com";
    private final String MAIL_KSENIA = "ksenia@gmail.com";
    private final String USER_DIMA = "Dima";
    private final String USER_KSENIA = "Ksenia";
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CatalogRepository catalogRepository;

    @Test
    void deleteShoppingCartIT() {
        var shoppingCart = getShoppingCart();

        shoppingCartRepository.delete(shoppingCart);

        assertThat(shoppingCartRepository.findById(shoppingCart.getId())).isEmpty();
    }

    @Test
    void saveShoppingCartIT() {
        var shoppingCart = getShoppingCart();

        assertThat(shoppingCart.getId()).isNotNull();
    }

    @Test
    void updateShoppingCartIT() {
        var shoppingCart = getShoppingCart();

        var updatedShoppingCart = shoppingCartRepository.findById(shoppingCart.getId());
        updatedShoppingCart.ifPresent(it -> it.setCreatedAt(LocalDate.now()));
        shoppingCartRepository.saveAndFlush(updatedShoppingCart.get());

        var actualResult = shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getCreatedAt())
                .isEqualTo(shoppingCart.getCreatedAt());
    }

    @Test
    void findByIdShoppingCartIT() {
        var shoppingCart = getShoppingCart();

        var actualResult = shoppingCartRepository.findById(shoppingCart.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(shoppingCart);

    }

    @Test
    void findAllShoppingCartIT() {
        var actualResult = shoppingCartRepository.findAll();

        assertThat(actualResult).hasSize(15);
    }
    @Test
    void getStatisticOfEachOrdersWithSumIT() {

        var actualResult = shoppingCartRepository.getStatisticOfEachOrdersWithSum();

        var orderSum = actualResult.stream()
                .map(it -> it.get(1, Integer.class))
                .collect(toList());

        assertAll(
                () -> assertThat(actualResult).hasSize(8),
                () -> assertThat(orderSum).containsExactlyInAnyOrder(
                        1000, 1100, 3400, 2000, 1450, 2000,  1450, 3350
                )
        );
    }

    @Test
    void findUsersWhoMadeAnOrderOfSpecificProductIT() {

        var productFilter = getProductFilter();

        var actualResult = shoppingCartRepository.findUsersWhoMadeOrderOfSpecificProduct(productFilter);

        var firstName = actualResult.stream()
                .map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());

        var emailResult = actualResult.stream()
                .map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());

        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(firstName).containsExactlyInAnyOrder(USER_DIMA, USER_KSENIA),
                () -> assertThat(emailResult).containsExactlyInAnyOrder(MAIL_DIMA, MAIL_KSENIA)
        );
    }

    private static ProductFilter getProductFilter() {
        var productFilter = ProductFilter.builder()
                .brand(Brand.SONY)
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .model("XM4")
                .build();
        return productFilter;
    }

    private ShoppingCart getShoppingCart() {
        var user = MocksForRepository.getUser();
        userRepository.save(user);
        var order = MocksForRepository.getOrder(user);
        orderRepository.save(order);
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);
        var shoppingCart = MocksForRepository.shoppingCart(order, product);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCart;
    }


}




