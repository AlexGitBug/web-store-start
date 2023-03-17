package util;

import entity.Catalog;
import entity.Product;
import entity.enums.Brand;
import entity.enums.Color;

import java.time.LocalDate;

public class TestCreateObjectForRepository {

    public static Product getProduct() {
        return Product.builder()
                .catalog(getCatalog())
                .brand(Brand.SONY)
                .model("test")
                .dateOfRelease(LocalDate.of(2021, 1, 1))
                .price(100)
                .color(Color.BLACK)
                .image("test")
                .build();
    }

    public static Catalog getCatalog() {
        Catalog catalog = Catalog.builder()
                .category("categoryName")
                .build();
        return catalog;
    }
}
