package com.dmdev.webStore.unit.mapper.catalog;

import com.dmdev.webStore.dto.catalog.CatalogCreateEditDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.mapper.catalog.CatalogCreateEditMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CatalogCreateEditMapperTest {

    @InjectMocks
    private CatalogCreateEditMapper catalogCreateEditMapper;

    @Test
    void map() {
        var catalog = getCatalog();

        var actualResult = catalogCreateEditMapper.map(getCatalogCreateEditDto());

        assertEquals(catalog, actualResult);
    }

    private Catalog getCatalog() {
        return Catalog.builder()
                .category("test")
                .build();
    }
    private CatalogCreateEditDto getCatalogCreateEditDto() {
        return new CatalogCreateEditDto("test");
    }
}
