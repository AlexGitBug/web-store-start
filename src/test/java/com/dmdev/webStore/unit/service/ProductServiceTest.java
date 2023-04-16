package com.dmdev.webStore.unit.service;

import com.dmdev.webStore.dao.repository.ProductRepository;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dto.CatalogReadDto;
import com.dmdev.webStore.dto.ProductCreateEditDto;
import com.dmdev.webStore.dto.ProductReadDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.mapper.ProductCreateEditMapper;
import com.dmdev.webStore.mapper.ProductReadMapper;
import com.dmdev.webStore.service.ProductService;
import liquibase.pro.packaged.F;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final Integer FIND_BY_ID = 99;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductReadMapper productReadMapper;
    @Mock
    private ProductCreateEditMapper productCreateEditMapper;
    @InjectMocks
    private ProductService productService;

    @Test
    void findByIdSuccess() {
        var catalog = getCatalog();
        var product = getProduct(catalog);
        var catalogReadDto = getCatalogReadDto();
        var productReadDto = getProductReadDto(catalogReadDto);
        doReturn(Optional.of(product)).when(productRepository).findById(product.getId());
        doReturn(productReadDto).when(productReadMapper).map(product);

        var actualResult = productService.findById(product.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(productReadDto);
    }

    @Test
    void findByIdNotSuccess() {
        doReturn(Optional.empty()).when(productRepository).findById(FIND_BY_ID);

        var actualResult = productService.findById(FIND_BY_ID);

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(productReadMapper);
    }

    @Test
    void findAll() {
        var catalog = getCatalog();
        var product = getProduct(catalog);
        var product1 = getProduct1(catalog);
        var catalogReadDto = getCatalogReadDto();
        var productReadDto = getProductReadDto(catalogReadDto);
        var productReadDto1 = getProductReadDto(catalogReadDto);
        List<Product> productList = List.of(product, product1);
        List<ProductReadDto> expectedDtoList = List.of(productReadDto, productReadDto1);
        doReturn(productList).when(productRepository).findAll();
        doReturn(expectedDtoList.get(0), expectedDtoList.get(1)).when(productReadMapper).map(any(Product.class));

        List<ProductReadDto> actualResult = productService.findAll();

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(expectedDtoList);
        assertThat(actualResult).containsExactlyInAnyOrder(productReadDto, productReadDto1);
        verify(productRepository).findAll();
    }


    @Test
    void findListOfProductsEq() {
        var filter = ProductFilter.builder()
                .brand(Brand.APPLE)
                .build();
        List<Product> productList = List.of(getProduct(getCatalog()), getProduct1(getCatalog()));
        var pageable = PageRequest.of(0, 3);
        Page<Product> productPage = new PageImpl<>(productList, pageable, 2L);
        doReturn(productPage).when(productRepository).findListOfProductsEq(filter);

        var actualResult = productService.findListOfProductsEq(filter, pageable);

        assertAll(
                () -> assertThat(actualResult).isNotNull(),
                () -> assertThat(actualResult.getContent().size()).isEqualTo(2),
                () -> assertThat(actualResult.getTotalPages()).isEqualTo(1),
                () -> assertThat(actualResult.getTotalElements()).isEqualTo(2)
        );
    }


    @Test
    void create() {
        var productCreateEditDto = getProductCreateEditDto();
        var catalog = getCatalog();
        var product = getProduct(catalog);
        var catalogReadDto = getCatalogReadDto();
        var productReadDto = getProductReadDto(catalogReadDto);
        doReturn(product).when(productCreateEditMapper).map(productCreateEditDto);
        doReturn(product).when(productRepository).save(product);
        doReturn(productReadDto).when(productReadMapper).map(product);

        var actualResult = productService.create(productCreateEditDto);

        assertThat(actualResult).isEqualTo(productReadDto);
        verify(productRepository).save(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void nullPointerExceptionCreate() {
        ProductCreateEditDto productDto = null;

        assertThrows(NullPointerException.class, () -> productService.create(productDto));
        verifyNoInteractions(productCreateEditMapper, productRepository, productReadMapper);
    }

    @Test
    void update() {
        var productCreateEditDto = getProductCreateEditDto();
        var catalog = getCatalog();
        var product = getProduct(catalog);
        var updatedProduct = getProduct1(catalog);
        var catalogReadDto = getCatalogReadDto();
        var productReadDto = getProductReadDto(catalogReadDto);
        doReturn(Optional.of(product)).when(productRepository).findById(FIND_BY_ID);
        doReturn(updatedProduct).when(productCreateEditMapper).map(productCreateEditDto, product);
        doReturn(updatedProduct).when(productRepository).saveAndFlush(updatedProduct);
        doReturn(productReadDto).when(productReadMapper).map(updatedProduct);

        var actualResult = productService.update(FIND_BY_ID, productCreateEditDto);


        assertAll(
                () -> assertThat(actualResult).isPresent(),
                () -> assertThat(actualResult.get()).isEqualTo(productReadDto),
                () -> verify(productRepository).saveAndFlush(updatedProduct)
        );
    }

    @Test
    void updateNotFound() {
        var productCreateEditDto = getProductCreateEditDto();
        doReturn(Optional.empty()).when(productRepository).findById(FIND_BY_ID);

        var actualResult = productService.update(FIND_BY_ID, productCreateEditDto);

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(productCreateEditMapper, productReadMapper);
    }

    @Test
    void delete() {
        var catalog = getCatalog();
        var product = getProduct(catalog);
        doReturn(Optional.of(product)).when(productRepository).findById(FIND_BY_ID);

        var actualResult = productService.delete(FIND_BY_ID);

        assertThat(actualResult).isTrue();
        verify(productService).delete(FIND_BY_ID);
        verify(productRepository).flush();

    }

    @Test
    void deleteFalse() {
        doReturn(Optional.empty()).when(productRepository).findById(FIND_BY_ID);

        var actualResult = productService.delete(FIND_BY_ID);

        assertThat(actualResult).isFalse();
    }

    private Product getProduct(Catalog catalog) {
        return Product.builder()
                .id(99)
                .model("test")
                .brand(Brand.APPLE)
                .dateOfRelease(LocalDate.now())
                .price(123)
                .color(Color.BLACK)
                .image("test")
                .catalog(catalog)
                .build();
    }

    private Product getProduct1(Catalog catalog) {
        return Product.builder()
                .id(100)
                .model("test")
                .brand(Brand.APPLE)
                .dateOfRelease(LocalDate.now())
                .price(123)
                .color(Color.BLACK)
                .image("test")
                .catalog(catalog)
                .build();
    }

    private Catalog getCatalog() {
        return Catalog.builder()
                .id(9)
                .category("test")
                .build();
    }

    private CatalogReadDto getCatalogReadDto() {
        return new CatalogReadDto(9, "test");
    }

    private ProductReadDto getProductReadDto(CatalogReadDto catalogReadDto) {
        return new ProductReadDto(99, "test", LocalDate.now(), 123, "test", Color.BLACK, Brand.APPLE, catalogReadDto);
    }

    private ProductReadDto getProductReadDto1(CatalogReadDto catalogReadDto) {
        return new ProductReadDto(100, "test", LocalDate.now(), 123, "test", Color.BLACK, Brand.APPLE, catalogReadDto);
    }

    private ProductCreateEditDto getProductCreateEditDto() {
        return new ProductCreateEditDto("test", LocalDate.now(), 123, "test", Color.BLACK, Brand.APPLE, 9);
    }


}