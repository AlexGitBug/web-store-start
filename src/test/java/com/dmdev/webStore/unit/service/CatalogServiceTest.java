package com.dmdev.webStore.unit.service;

import com.dmdev.webStore.dto.catalog.CatalogCreateEditDto;
import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.mapper.catalog.CatalogCreateEditMapper;
import com.dmdev.webStore.mapper.catalog.CatalogReadMapper;
import com.dmdev.webStore.repository.CatalogRepository;
import com.dmdev.webStore.service.CatalogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {

    private static final Integer FIND_BY_ID = 99;
    @Mock
    private CatalogRepository catalogRepository;
    @Mock
    private CatalogReadMapper catalogReadMapper;
    @Mock
    private CatalogCreateEditMapper catalogCreateEditMapper;
    @InjectMocks
    private CatalogService catalogService;

    @Test
    void findByIdSuccess() {
        Catalog catalog = getCatalog();
        CatalogReadDto expectedCatalogReadDto = getCatalogReadDto();
        doReturn(Optional.of(catalog)).when(catalogRepository).findById(catalog.getId());
        doReturn(expectedCatalogReadDto).when(catalogReadMapper).map(catalog);

        Optional<CatalogReadDto> actualResult = catalogService.findById(catalog.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(expectedCatalogReadDto);

    }

    @Test
    void findByIdNotSuccess() {
        doReturn(Optional.empty()).when(catalogRepository).findById(any());

        Optional<CatalogReadDto> actualResult = catalogService.findById(any());

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(catalogReadMapper);
    }

    @Test
    void findAll() {
        Catalog catalog = getCatalog();
        Catalog catalog1 = getCatalog1();
        CatalogReadDto catalogReadDto = getCatalogReadDto();
        CatalogReadDto catalogReadDto1 = getCatalogReadDto1();
        List<Catalog> catalogList = List.of(catalog, catalog1);
        List<CatalogReadDto> expectedCatalogDtoList = List.of(catalogReadDto, catalogReadDto1);
        doReturn(catalogList).when(catalogRepository).findAll();
        doReturn(expectedCatalogDtoList.get(0), expectedCatalogDtoList.get(1))
                .when(catalogReadMapper).map(any(Catalog.class));

        List<CatalogReadDto> actualResult = catalogService.findAll();

        assertThat(actualResult).hasSize(2);
        assertThat(actualResult).isEqualTo(expectedCatalogDtoList);
        assertThat(actualResult).containsExactlyInAnyOrder(catalogReadDto, catalogReadDto1);
    }

    @Test
    void create() {
        CatalogCreateEditDto catalogCreateEditDto = getCatalogCreateEditDto();
        Catalog catalog = getCatalog();
        CatalogReadDto catalogReadDto = getCatalogReadDto();
        doReturn(catalog).when(catalogCreateEditMapper).map(catalogCreateEditDto);
        doReturn(catalog).when(catalogRepository).save(catalog);
        doReturn(catalogReadDto).when(catalogReadMapper).map(catalog);

        CatalogReadDto actualResult = catalogService.create(catalogCreateEditDto);

        assertThat(actualResult).isEqualTo(catalogReadDto);
        verify(catalogRepository).save(catalog);
    }

    @Test
    void nullPointerExceptionCreate() {
        CatalogCreateEditDto catalogCreateEditDto = null;

        assertThrows(NullPointerException.class, () -> catalogService.create(catalogCreateEditDto));
        verifyNoInteractions(catalogCreateEditMapper, catalogRepository, catalogReadMapper);
    }

    @Test
    void update() {
        CatalogCreateEditDto catalogCreateEditDto = getCatalogCreateEditDto();
        Catalog catalog = getCatalog();
        Catalog updatedCatalog = getCatalog();
        CatalogReadDto catalogReadDto = getCatalogReadDto();
        doReturn(Optional.of(catalog)).when(catalogRepository).findById(catalog.getId());
        doReturn(updatedCatalog).when(catalogCreateEditMapper).map(catalogCreateEditDto, catalog);
        doReturn(updatedCatalog).when(catalogRepository).saveAndFlush(updatedCatalog);
        doReturn(catalogReadDto).when(catalogReadMapper).map(updatedCatalog);

        Optional<CatalogReadDto> actualResult = catalogService.update(catalog.getId(), catalogCreateEditDto);

        assertAll(
                () -> assertThat(actualResult).isPresent(),
                () -> assertThat(actualResult.get()).isEqualTo(catalogReadDto),
                () -> verify(catalogRepository).saveAndFlush(updatedCatalog)
        );
    }

    @Test
    void delete() {
        Catalog catalog = getCatalog();
        doReturn(Optional.ofNullable(catalog)).when(catalogRepository).findById(catalog.getId());

        boolean actualResult = catalogService.delete(catalog.getId());

        assertThat(actualResult).isTrue();
    }

    @Test
    void deleteFalse() {
        doReturn(Optional.empty()).when(catalogRepository).findById(FIND_BY_ID);

        boolean actualResult = catalogService.delete(FIND_BY_ID);

        assertThat(actualResult).isFalse();
    }



    private Catalog getCatalog() {
        return Catalog.builder()
                .id(9)
                .category("test")
                .build();
    }

    private Catalog getCatalog1() {
        return Catalog.builder()
                .id(10)
                .category("test")
                .build();
    }

    private CatalogReadDto getCatalogReadDto() {
        return new CatalogReadDto(9, "test");
    }

    private CatalogReadDto getCatalogReadDto1() {
        return new CatalogReadDto(10, "test");
    }

    private CatalogCreateEditDto getCatalogCreateEditDto() {
        return new CatalogCreateEditDto("test");
    }
}

