//package dao.repository.integration;
//
//
//import com.dmdev.webStore.dao.repository.CatalogRepository;
//import com.dmdev.webStore.dao.repository.OrderRepository;
//import com.dmdev.webStore.dao.repository.ProductRepository;
//import com.dmdev.webStore.dao.repository.ShoppingCartRepository;
//import com.dmdev.webStore.dao.repository.UserRepository;
//import com.dmdev.webStore.dao.repository.filter.ProductFilter;
//import com.dmdev.webStore.entity.Catalog;
//import com.dmdev.webStore.entity.ShoppingCart;
//import com.dmdev.webStore.entity.enums.Brand;
//
//import dao.repository.util.TestDelete;
//import dao.repository.integration.annotation.IT;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import dao.repository.util.MocksForRepository;
//import dao.repository.util.TestDataImporter;
//
//import javax.persistence.EntityManager;
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static java.util.stream.Collectors.toList;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@IT
//@RequiredArgsConstructor
//public class ShoppingCartRepositoryIT {
//
//    private final String MAIL_DIMA = "dima@gmail.com";
//    private final String MAIL_KSENIA = "ksenia@gmail.com";
//    private final String USER_DIMA = "Dima";
//    private final String USER_KSENIA = "Ksenia";
//    private final ShoppingCartRepository shoppingCartRepository;
//    private final ProductRepository productRepository;
//    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
//    private final CatalogRepository catalogRepository;
//    private final EntityManager entityManager;
//
//    @BeforeEach
//    void deleteAllData() {
//        TestDelete.deleteAll(entityManager);
//    }
//    @Test
//    void deleteShoppingCartIT() {
//        var user = MocksForRepository.getUser();
//        userRepository.save(user);
//        var order = MocksForRepository.getOrder(user);
//        orderRepository.save(order);
//        var catalog = MocksForRepository.getCatalog();
//        catalogRepository.save(catalog);
//        var product = MocksForRepository.getProduct(catalog);
//        productRepository.save(product);
//        var shoppingCart = MocksForRepository.shoppingCart(order, product);
//        shoppingCartRepository.save(shoppingCart);
//
//        shoppingCartRepository.delete(shoppingCart);
//
//        assertThat(shoppingCartRepository.findById(shoppingCart.getId())).isEmpty();
//    }
//
//    @Test
//    void saveShoppingCartIT() {
//        var user = MocksForRepository.getUser();
//        userRepository.save(user);
//        var order = MocksForRepository.getOrder(user);
//        orderRepository.save(order);
//        var catalog = MocksForRepository.getCatalog();
//        catalogRepository.save(catalog);
//        var product = MocksForRepository.getProduct(catalog);
//        productRepository.save(product);
//        var shoppingCart = MocksForRepository.shoppingCart(order, product);
//
//        shoppingCartRepository.save(shoppingCart);
//
//        assertThat(shoppingCartRepository.findById(shoppingCart.getId())).contains(shoppingCart);
//    }
//
//    @Test
//    void updateShoppingCartIT() {
//        var user = MocksForRepository.getUser();
//        userRepository.save(user);
//        var order = MocksForRepository.getOrder(user);
//        orderRepository.save(order);
//        var catalog = MocksForRepository.getCatalog();
//        catalogRepository.save(catalog);
//        var product = MocksForRepository.getProduct(catalog);
//        productRepository.save(product);
//        var shoppingCart = MocksForRepository.shoppingCart(order, product);
//        shoppingCartRepository.save(shoppingCart);
//
//        var updatedShoppingCart = shoppingCartRepository.findById(shoppingCart.getId());
//        updatedShoppingCart.ifPresent(it -> it.setCreatedAt(LocalDate.now()));
//        shoppingCartRepository.update(updatedShoppingCart.get());
//
//        var actualResult = shoppingCartRepository.findById(shoppingCart.getId());
//        assertThat(actualResult).contains(shoppingCart);
//    }
//
//    @Test
//    void findByIdShoppingCartIT() {
//        var user = MocksForRepository.getUser();
//        userRepository.save(user);
//        var order = MocksForRepository.getOrder(user);
//        orderRepository.save(order);
//        var catalog = MocksForRepository.getCatalog();
//        catalogRepository.save(catalog);
//        var product = MocksForRepository.getProduct(catalog);
//        productRepository.save(product);
//        var shoppingCart = MocksForRepository.shoppingCart(order, product);
//        shoppingCartRepository.save(shoppingCart);
//
//        var actualResult = shoppingCartRepository.findById(shoppingCart.getId());
//        assertThat(actualResult).isPresent();
//        assertThat(actualResult).contains(shoppingCart);
//
//    }
//    @Test
//    void getStatisticOfEachOrdersWithSumIT() {
//        TestDataImporter.importData(entityManager);
//
//        var result = shoppingCartRepository.getStatisticOfEachOrdersWithSum();
//        assertThat(result).hasSize(8);
//
//        var orderSum = result.stream().map(it -> it.get(1, Integer.class)).collect(toList());
//        assertThat(orderSum).contains(1000, 1100, 3400, 2000, 1450, 1450, 4000, 3350);
//
//    }
//
//    @Test
//    void findUsersWhoMadeAnOrderOfSpecificProductIT() {
//        TestDataImporter.importData(entityManager);
//
//        var productFilter = ProductFilter.builder()
//                .brand(Brand.SONY)
//                .catalog(Catalog.builder()
//                        .category("Headphones")
//                        .build())
//                .model("XM4")
//                .build();
//
//        var results = shoppingCartRepository.findUsersWhoMadeOrderOfSpecificProduct(productFilter);
//        assertThat(results).hasSize(2);
//
//        var firstName = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getFirstName()).collect(toList());
//        assertThat(firstName).contains(USER_DIMA, USER_KSENIA);
//
//        var emailResult = results.stream().map(it -> it.getOrder().getUser().getPersonalInformation().getEmail()).collect(toList());
//        assertThat(emailResult).contains(MAIL_DIMA, MAIL_KSENIA);
//    }
//}
//
//
//
//
