package com.dmdev.webStore.mapper.catalog;

import com.dmdev.webStore.dto.catalog.CatalogCreateEditDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CatalogCreateEditMapper implements Mapper<CatalogCreateEditDto, Catalog> {


    @Override
    public Catalog map(CatalogCreateEditDto fromObject, Catalog toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Catalog map(CatalogCreateEditDto object) {
        Catalog catalog = new Catalog();
        copy(object, catalog);

        return catalog;
    }

    private void copy(CatalogCreateEditDto object, Catalog catalog) {
       catalog.setCategory(object.getCategory());
    }

}