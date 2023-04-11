package dao.repository.integration;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.dao.repository.ProductRepository;
import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;

import java.time.LocalDate;
import java.util.List;

import static com.dmdev.webStore.entity.enums.Brand.*;
import static com.dmdev.webStore.entity.enums.Brand.SONY;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class ProductRepositoryIT extends IntegrationTestBase {
    private final static String MODEL_13 = "13";
    private final static String MODEL_14 = "14";
    private final static String PRODUCT_CATEGORY_HEADPHONES = "Headphones";
    private final static String PRODUCT_CATEGORY_SMARTPHONE = "Smartphone";
    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;


    @Test
    void deleteProductIT() {
        Product product = getProduct();

        productRepository.delete(product);

        assertThat(productRepository.findById(product.getId())).isEmpty();
    }

    @Test
    void saveProductIT() {
        Product product = getProduct();

        assertThat(product.getId()).isNotNull();
    }

    @Test
    void updateProductIT() {
        Product product = getProduct();

        var updatedProduct = productRepository.findById(product.getId());
        updatedProduct.ifPresent(it -> it.setModel("test-update"));
        productRepository.saveAndFlush(updatedProduct.get());

        var actualResult = productRepository.findById(product.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getModel())
                .isEqualTo(product.getModel());

    }

    @Test
    void findByIdProductIT() {
        Product product = getProduct();

        var actualResult = productRepository.findById(product.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(product);
    }

    @Test
    void findAllProductIT() {
        var actualResult = productRepository.findAll();

        var models = actualResult.stream()
                .map(Product::getId)
                .toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(12),
                () -> assertThat(models).containsExactlyInAnyOrder(
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
                )
        );
    }

    @Test
    void findListOfProductsEqIT() {
        var productFilter = ProductFilter.builder()
                .catalog(MocksForRepository.getCatalogSmartphone())
                .brand(APPLE)
                .build();

        var actualResult = productRepository.findListOfProductsEq(productFilter);

        var categoryResult = actualResult.stream()
                .map(it -> it.getCatalog().getCategory())
                .collect(toList());

        var brandResult = actualResult.stream()
                .map(Product::getBrand)
                .collect(toList());

        assertAll(
                () -> assertThat(actualResult).hasSize(3),
                () -> assertThat(categoryResult).containsExactlyInAnyOrder(
                        PRODUCT_CATEGORY_SMARTPHONE, PRODUCT_CATEGORY_SMARTPHONE, PRODUCT_CATEGORY_SMARTPHONE),
                () -> assertThat(brandResult).containsExactlyInAnyOrder(
                        APPLE, APPLE, APPLE)
        );
    }

    @Test
    void findProductOfOneCategoryAndBrandBetweenTwoPriceIT() {
        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category(PRODUCT_CATEGORY_HEADPHONES)
                        .build())
                .brand(SONY)
                .priceA(100)
                .priceB(5000)
                .build();

        var actualResult = productRepository
                .findProductOfOneCategoryAndBrandBetweenTwoPrice(productFilter);

        var brands = actualResult.stream()
                .map(Product::getBrand).collect(toList());

        var price = actualResult.stream()
                .map(Product::getPrice).toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(4),
                () -> assertThat(brands).containsExactlyInAnyOrder(
                        SONY, SONY, SONY, SONY),
                () -> assertThat(price).containsExactlyInAnyOrder(
                        300, 350, 350, 400)
        );
    }

    @Test
    void findProductsOfBrandAndCategoryAndLtPriceIT() {
        var productFilter = ProductFilter.builder()
                .catalog(MocksForRepository.getCatalogSmartphone())
                .brand(APPLE)
                .price(1050)
                .build();

        var actualResult = productRepository.findProductsOfBrandAndCategoryAndLtPrice(productFilter);

        var brands = actualResult.stream()
                .map(Product::getBrand)
                .collect(toList());

        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(brands).containsExactly(
                        APPLE, APPLE)
        );
    }

    @Test
    void findProductsOfBrandAndCategoryAndGtPriceIT() {
        var productFilter = ProductFilter.builder()
                .catalog(MocksForRepository.getCatalogSmartphone())
                .brand(APPLE)
                .price(999)
                .build();

        var actualResult = productRepository.findProductsOfBrandAndCategoryAndGtPrice(productFilter);

        var model = actualResult.stream()
                .map(Product::getModel)
                .collect(toList());

        var brands = actualResult.stream()
                .map(Product::getBrand)
                .collect(toList());

        assertAll(
                () -> assertThat(actualResult).hasSize(2),
                () -> assertThat(model).containsExactlyInAnyOrder(MODEL_13, MODEL_14),
                () -> assertThat(brands).containsExactlyInAnyOrder(APPLE, APPLE)
        );
    }


    @Test
    void findAllProductOfBrandIT() {
        var actualResult = productRepository.findAllByBrand(SAMSUNG);

        var brands = actualResult.stream()
                .map(Product::getId).toList();

        assertAll(
                () -> assertThat(actualResult).hasSize(5),
                () -> assertThat(brands)
                        .containsExactlyInAnyOrder(4, 5, 6, 7, 12)
        );
    }

    @Test
    void findTopByOrderByPriceAsc() {
        var actualResult = productRepository.findTopByOrderByPriceAsc();
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(product -> assertEquals(12, product.getId()));
    }

    @Test
    void findTopByOrderByPriceDesc() {
        var actualResult = productRepository.findTopByOrderByPriceDesc();
        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(product -> assertEquals(7, product.getId()));
    }

    @Test
    void findAllProductsFromOrderIT() {
        var orderFilter = OrderFilter.builder()
                .deliveryDate(LocalDate.of(2022, 12, 10))
                .build();

        var actualResult = productRepository.findAllProductsFromOrder(orderFilter);
        assertThat(actualResult).hasSize(1);

        var model = actualResult.stream()
                .map(Product::getModel)
                .collect(toList());

        assertAll(
                () -> assertThat(actualResult).hasSize(1),
                () -> assertThat(model)
                        .containsExactlyInAnyOrder(MODEL_13)
        );
    }

    @Test
    void findAllByCatalog() {
        List<Product> actualResult = productRepository.findAllByCatalogCategory(PRODUCT_CATEGORY_HEADPHONES);

        List<Integer> products = actualResult.stream()
                .map(Product::getId).toList();


        assertAll(
                () -> assertThat(actualResult).hasSize(5),
                () -> assertThat(products)
                        .containsExactlyInAnyOrder(8, 9, 10, 11, 12)
        );
    }

    private Product getProduct() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);
        return product;
    }
}
