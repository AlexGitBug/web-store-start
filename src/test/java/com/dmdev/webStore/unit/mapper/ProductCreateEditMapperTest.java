package com.dmdev.webStore.unit.mapper;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.mapper.product.ProductCreateEditMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductCreateEditMapperTest {

    @InjectMocks
    private ProductCreateEditMapper productCreateEditMapper;

    @Test
    void mapCreateEditMapperTest() {
        ProductCreateEditDto productCreateEditDto = getProductCreateEditDto();

        Product actualResult = productCreateEditMapper.map(productCreateEditDto);

        assertThat(actualResult.getCatalog().getId())
                .isEqualTo(productCreateEditDto.getCatalogId());
    }

    private Catalog getCatalog() {
        return Catalog.builder()
                .id(1)
                .category("test")
                .build();
    }

    private ProductCreateEditDto getProductCreateEditDto() {
        return new ProductCreateEditDto("test", LocalDate.now(), 123,  Color.BLACK, Brand.APPLE, getCatalog().getId(), new MockMultipartFile("test", new byte[0]));
    }
}