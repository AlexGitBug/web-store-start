package com.dmdev.webStore.integration.service;

import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.service.ProductService;
import com.dmdev.webStore.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
public class ProductServiceIT extends IntegrationTestBase {

    private static final Integer PRODUCT_1 = 1;
    private static final Integer CATALOG_1 = 1;
    private final ProductService productService;

    @Test
    void findAll() {
        var result = productService.findAll();
        assertThat(result).hasSize(12);
    }

    @Test
    void findById() {
        var maybeUser = productService.findById(PRODUCT_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(product -> assertEquals(Brand.APPLE, product.getBrand()));
    }

    @Test
    void create() {
        MultipartFile file = null;
        var productDto = new ProductCreateEditDto(
                "test-model",
                LocalDate.now(),
                123,
                Color.BLACK,
                Brand.APPLE,
                1,
                null
        );

        var actualResult = productService.create(productDto);

        assertEquals(productDto.getModel(), actualResult.getModel());
        assertEquals(productDto.getDateOfRelease(), actualResult.getDateOfRelease());
        assertEquals(productDto.getPrice(), actualResult.getPrice());
        assertEquals(productDto.getImage(), actualResult.getImage());
        assertEquals(productDto.getColor(), actualResult.getColor());
        assertEquals(productDto.getBrand(), actualResult.getBrand());
        assertEquals(productDto.getCatalogId(), actualResult.getCatalog().getId());
    }

    @Test
    void update() {
        MultipartFile file = null;
        var productDto = new ProductCreateEditDto(
                "test-model",
                LocalDate.now(),
                123,
                Color.BLACK,
                Brand.APPLE,
                1,
                null
        );

        var actualResult = productService.update(PRODUCT_1, productDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(product-> {
            assertEquals(productDto.getModel(), product.getModel());
            assertEquals(productDto.getDateOfRelease(), product.getDateOfRelease());
            assertEquals(productDto.getPrice(), product.getPrice());
            assertEquals(productDto.getImage(), product.getImage());
            assertEquals(productDto.getColor(), product.getColor());
            assertEquals(productDto.getBrand(), product.getBrand());
            assertEquals(productDto.getCatalogId(), product.getCatalog().getId());
        });
    }

    @Test
    void delete() {
        assertFalse(productService.delete(-120));
        assertTrue(productService.delete(PRODUCT_1));
    }

}
