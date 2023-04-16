package com.dmdev.webStore.integration.mapper;

import com.dmdev.webStore.dto.CatalogReadDto;
import com.dmdev.webStore.dto.ProductCreateEditDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.mapper.ProductCreateEditMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductCreateEditMapperTest {

    @InjectMocks
    private ProductCreateEditMapper productCreateEditMapper;

    @Test
    void mapCreateEditMapperTest() {

        CatalogReadDto catalogReadDto = new CatalogReadDto(1,"test");

        var productCreateEditDto = new ProductCreateEditDto(
                "test",
                LocalDate.of(2020, 9, 12),
                950,
                "test",
                Color.BLACK,
                Brand.APPLE,
               catalogReadDto.getId()
        );

        var actualResult = productCreateEditMapper.map(productCreateEditDto);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getModel()).isEqualTo(productCreateEditDto.getModel());
    }
}