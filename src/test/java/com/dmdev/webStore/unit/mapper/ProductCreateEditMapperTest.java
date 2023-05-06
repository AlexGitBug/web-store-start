package com.dmdev.webStore.unit.mapper;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.mapper.catalog.CatalogReadMapper;
import com.dmdev.webStore.mapper.product.ProductCreateEditMapper;
import com.dmdev.webStore.repository.CatalogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductCreateEditMapperTest {

    @Mock
    private CatalogRepository catalogRepository;
    @Mock
    private CatalogReadMapper catalogReadMapper;
    @InjectMocks
    private ProductCreateEditMapper productCreateEditMapper;

    @Test
    void mapCreateEditMapperTest() {
////        ProductCreateEditDto productCreateEditDto = getProductCreateEditDto();
//
//        Product actualResult = productCreateEditMapper.map(productCreateEditDto);
//
//        assertThat(actualResult.getCatalog().getId())
//                .isEqualTo(productCreateEditDto.getCatalogId());
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

    private Catalog getCatalog(Integer catalogId) {
        return Optional.ofNullable(catalogId)
                .flatMap(id -> catalogRepository.findById(id))
                .orElse(null);
    }
}