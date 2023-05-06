package com.dmdev.webStore.unit.mapper;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.mapper.catalog.CatalogReadMapper;
import com.dmdev.webStore.mapper.product.ProductReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ProductReadMapperTest {

    @Mock
    private CatalogReadMapper catalogReadMapper;
    @InjectMocks
    private ProductReadMapper productReadMapper;

    @Test
    void map() {
        var product = getProduct();
        var expectedResult = getProductReadDto();

        var actualResult = productReadMapper.map(product);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private Catalog getCatalog() {
        return Catalog.builder()
                .id(1)
                .category("test")
                .build();
    }

    private Product getProduct() {
        return Product.builder()
                .id(1)
                .model("test")
                .brand(Brand.APPLE)
                .dateOfRelease(LocalDate.now())
                .price(123)
                .color(Color.BLACK)
                .image("test")
                .catalog(getCatalog())
                .build();
    }

    private ProductReadDto getProductReadDto() {
        CatalogReadDto catalog = Optional.ofNullable(getCatalog())
            .map(catalogReadMapper::map)
            .orElse(null);
        return new ProductReadDto(1, "test", LocalDate.now(), 123, "test", Color.BLACK, Brand.APPLE, catalog);
    }

}