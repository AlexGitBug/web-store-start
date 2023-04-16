package com.dmdev.webStore.integration.mapper;

import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.mapper.CatalogReadMapper;
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

        assertThat(actualResult.getId()).isEqualTo(catalog.getId());
    }

}