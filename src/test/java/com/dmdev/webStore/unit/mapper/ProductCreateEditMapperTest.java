package com.dmdev.webStore.unit.mapper;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.mapper.product.ProductCreateEditMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductCreateEditMapperTest {

    @InjectMocks
    private ProductCreateEditMapper productCreateEditMapper;

    @Test
    void mapCreateEditMapperTest() {
        MultipartFile file = null;

        CatalogReadDto catalogReadDto = new CatalogReadDto(1,"test");

        var productCreateEditDto = new ProductCreateEditDto(
                "test",
                LocalDate.of(2020, 9, 12),
                950,
                Color.BLACK,
                Brand.APPLE,
               catalogReadDto.getId(),
                null
        );

        var actualResult = productCreateEditMapper.map(productCreateEditDto);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getModel()).isEqualTo(productCreateEditDto.getModel());
    }
}