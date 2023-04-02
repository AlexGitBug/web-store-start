package dao.repository.integration;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.dao.repository.ProductRepository;
import com.dmdev.webStore.dao.repository.filter.OrderFilter;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;


import dao.repository.util.TestDelete;
import dao.repository.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.repository.util.MocksForRepository;
import dao.repository.util.TestDataImporter;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
@IT
@RequiredArgsConstructor
public class ProductRepositoryIT {
    private final ProductRepository productRepository;
    private final CatalogRepository catalogRepository;

    private final EntityManager entityManager;

    @BeforeEach
    void deleteAllData() {
        TestDelete.deleteAll(entityManager);
    }

    @Test
    void deleteProductIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);

        productRepository.delete(product);

        assertThat(productRepository.findById(product.getId())).isEmpty();
    }

    @Test
    void saveProductIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);

        productRepository.save(product);

        assertThat(productRepository.findById(product.getId())).contains(product);

//        var actualResult = entityManager.find(Product.class, product.getId());
//        entityManager.getTransaction().commit();
//        assertThat(actualResult).isEqualTo(product);
    }

    @Test
    void updateProductIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);

        var result = entityManager.find(Product.class, product.getId());
        result.setModel("test-update");
        productRepository.update(result);

        var actualResult = entityManager.find(Product.class, result.getId());
        assertThat(actualResult).isEqualTo(product);

    }

    @Test
    void findByIdProductIT() {
        var catalog = MocksForRepository.getCatalog();
        catalogRepository.save(catalog);
        var product = MocksForRepository.getProduct(catalog);
        productRepository.save(product);

        var actualResult = productRepository.findById(product.getId());
        assertThat(actualResult).isPresent();
        assertThat(actualResult).contains(product);
//        var actualResult = entityManager.find(Product.class, product.getId());
//        assertThat(actualResult).isEqualTo(product);
    }

    @Test
    void findListOfProductsEqIT() {
        TestDataImporter.importData(entityManager);

        var productFilter = ProductFilter.builder()
                .catalog(MocksForRepository.getCatalog())
                .brand(Brand.APPLE)
                .build();

        var results = productRepository.findListOfProductsEq(productFilter);
        assertThat(results).hasSize(3);

        var categoryResult = results.stream().map(it -> it.getCatalog().getCategory()).collect(toList());
        assertThat(categoryResult).contains(productFilter.getCatalog().getCategory());

        var brandResult = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brandResult).contains(productFilter.getBrand());
    }

    @Test
    void findProductOfOneCategoryAndBrandBetweenTwoPriceIT() {
        TestDataImporter.importData(entityManager);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Headphones")
                        .build())
                .brand(Brand.SONY)
                .build();

        var results = productRepository.findProductOfOneCategoryAndBrandBetweenTwoPrice(productFilter, 100, 5000);
        assertThat(results).hasSize(4);

        var brands = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brands).contains(productFilter.getBrand());

        var price = results.stream().map(Product::getPrice).toList();
        assertThat(price).contains(300, 350, 350, 400);
    }

    @Test
    void findProductsOfBrandAndCategoryAndGtPriceIT() {
        TestDataImporter.importData(entityManager);

        var productFilter = ProductFilter.builder()
                .catalog(Catalog.builder()
                        .category("Smartphone")
                        .build())
                .brand(Brand.APPLE)
                .price(999)
                .build();

        var results = productRepository.findProductsOfBrandAndCategoryAndGtPrice(productFilter);
        assertThat(results).hasSize(2);

        var model = results.stream().map(Product::getModel).collect(toList());
        assertThat(model).contains("13", "14");

        var brands = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brands).contains(Brand.APPLE);
    }

    @Test
    void findProductsOfBrandAndCategoryAndLtPriceIT() {
        TestDataImporter.importData(entityManager);

        var productFilter = ProductFilter.builder()
                .catalog(MocksForRepository.getCatalog())
                .brand(Brand.APPLE)
                .price(1050)
                .build();

        var results = productRepository.findProductsOfBrandAndCategoryAndLtPrice(productFilter);
        assertThat(results).hasSize(2);

        var brands = results.stream().map(Product::getBrand).collect(toList());
        assertThat(brands).contains(Brand.APPLE);

    }


    @Test
    void findAllProductOfBrandIT() {
        TestDataImporter.importData(entityManager);

        var productFilter1 = ProductFilter.builder()
                .brand(Brand.SAMSUNG)
                .build();

        var results = productRepository.findAllProductOfBrand(productFilter1);
        assertThat(results).hasSize(5);

        var brands = results.stream().map(Product::getBrand).toList();
        assertThat(brands).contains(Brand.SAMSUNG);
    }

    @Test
    void findMinPriceOfProductIT() {
        TestDataImporter.importData(entityManager);

        var result = productRepository.findMinPriceOfProduct();

        assertThat(result.getPrice()).isEqualTo(150);
    }

    @Test
    void findAllProductsFromOrderIT() {
        TestDataImporter.importData(entityManager);

        var orderFilter = OrderFilter.builder()
                .deliveryDate( LocalDate.of(2022, 12, 10))
                .build();
        var results = productRepository.findAllProductsFromOrder(orderFilter);
        assertThat(results).hasSize(1);
        var model = results.stream().map(Product::getModel).collect(toList());
        assertThat(model).contains("13");
    }
}
