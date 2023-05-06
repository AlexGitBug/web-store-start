package com.dmdev.webStore.unit.mapper.catalog;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.mapper.catalog.CatalogReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CatalogReadMapperTest {

    @InjectMocks
    private CatalogReadMapper catalogReadMapper;

    @Test
    void mapCatalogReadTest() {


        var catalog = Catalog.builder()
                .id(1)
                .category("test")
                .build();

        var actualResult = catalogReadMapper.map(catalog);

        var expectedResult = new CatalogReadDto(1, "test");
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}
