package com.dmdev.webStore.mapper.catalog;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CatalogReadMapper implements Mapper<Catalog, CatalogReadDto> {

    @Override
    public CatalogReadDto map(Catalog object) {
        return new CatalogReadDto(
                object.getId(),
                object.getCategory()
        );
    }
}
