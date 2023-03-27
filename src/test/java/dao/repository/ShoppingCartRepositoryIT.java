package dao.repository;

import com.dmdev.webStore.dao.repository.*;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dao.repository.filter.UserFilter;
import dao.repository.initProxy.ProxySessionTestBase;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.ShoppingCart;
import com.dmdev.webStore.entity.embeddable.PersonalInformation;
import com.dmdev.webStore.entity.enums.Brand;

import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;
import dao.repository.util.TestDataImporter;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartRepositoryIT extends ProxySessionTestBase {
    private final ShoppingCartRepository shoppingCartRepository = applicationContext.getBean(ShoppingCartRepository.class);
    private final ProductRepository productRepository = applicationContext.getBean(ProductRepository.class);
    private final OrderRepository orderRepository = applicationContext.getBean(OrderRepository.class);
    private final UserRepository userRepository = applicationContext.getBean(UserRepository.class);
    private final CatalogRepository catalogRepository = applicationContext.getBean(CatalogRepository.class);

    @Test
    void deleteShoppingCart() {
        entityManager.getTransaction().begin();
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

        shoppingCartRepository.delete(shoppingCart);

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isNull();

    }

    @Test
    void saveShoppingCart() {
        entityManager.getTransaction().begin();

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

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isNotNull();

    }

    @Test
    void updateShoppingCart() {
        entityManager.getTransaction().begin();
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

        var result = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        result.setCreatedAt(LocalDate.now());
        shoppingCartRepository.update(shoppingCart);

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        entityManager.getTransaction().commit();
        assertThat(actualResult).isEqualTo(shoppingCart);

    }

    @Test
    void findByIdShoppingCart() {
        entityManager.getTransaction().begin();
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

        var actualResult = entityManager.find(ShoppingCart.class, shoppingCart.getId());
        entityManager.getTransaction().commit();

        assertThat(actualResult).isEqualTo(shoppingCart);

    }
    @Test
    void getStatisticOfEachOrdersWithSum() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var result = shoppingCartRepository.getStatisticOfEachOrdersWithSum();
        entityManager.getTransaction().commit();
        assertThat(result).hasSize(8);

        var orderSum = result.stream().map(it -> it.get(1, Integer.class)).collect(toList());
        assertThat(orderSum).contains(1000, 1100, 3400, 2000, 1450, 1450, 4000, 3350);

    }

    @Test
    void findUsersWhoMadeAnOrderOfSpecificProduct() {
        TestDataImporter.importData(sessionFactory);
        entityManager.getTransaction().begin();

        var productFilter = ProductFilter.builder()
                .brand(Brand.SONY)
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .model("XM4")
                .build();

        var results = shoppingCartRepository.findUsersWhoMadeOrderOfSpecificProduct(productFilter);
        entityManager.getTransaction().commit();
        assertThat(results).hasSize(2);

        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
        assertThat(firstName).contains("Dima", "Ksenia");

        var telephoneResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getTelephone()).collect(toList());
        assertThat(telephoneResult).contains("321-32-32", "123-32-32");

        var emailResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
        assertThat(emailResult).contains("dima@gmail.com", "ksenia@gmail.com");


    }
}




