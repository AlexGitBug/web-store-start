package com.dmdev.webStore.mapper;

import com.dmdev.webStore.dto.CatalogReadDto;
import com.dmdev.webStore.entity.Catalog;
import org.springframework.stereotype.Component;

@Component
public class CatalogReadMapper implements Mapper<Catalog, CatalogReadDto>{

    @Override
    public CatalogReadDto map(Catalog object) {
        return new CatalogReadDto(
                object.getId(),
                object.getCategory()
        );
    }
}
