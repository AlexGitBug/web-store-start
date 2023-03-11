package entity;

import entity.enums.Brand;
import entity.enums.Color;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    void TestExample() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.get(Product.class, 1);

            session.getTransaction().commit();
        }
    }

    private static final String URL = "C:/work/photo/";
    @Test
    void checkProductSaveAndGet() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            var product = getProduct();
            session.beginTransaction();

            session.save(product);

            session.getTransaction().commit();
            var actualResult = session.get(Product.class, product.getId());
            assertThat(actualResult).isEqualTo(product);
        }

    }

    private Product getProduct() {
        return Product.builder()
                .catalog(getCatalog())
                .brand(Brand.APPLE)
                .model("13")
                .dateOfRelease(LocalDate.of(2020, 1, 10))
                .price(150)
                .color(Color.BLACK)
                .image(URL+"100.jpg")
                .build();

    }
    private Catalog getCatalog() {
        return Catalog.builder()
                .category("Smartphone")
                .build();
    }
}
