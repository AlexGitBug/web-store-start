package com.dmdev.webStore.mapper;

import com.dmdev.webStore.dto.CatalogReadDto;
import com.dmdev.webStore.dto.ProductReadDto;
import com.dmdev.webStore.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {

    private final CatalogReadMapper catalogReadMapper;

    @Override
    public ProductReadDto map(Product object) {
        CatalogReadDto catalog = Optional.ofNullable(object.getCatalog())
                .map(catalogReadMapper::map)
                .orElse(null);
        return new ProductReadDto(
                object.getId(),
                object.getModel(),
                object.getDateOfRelease(),
                object.getPrice(),
                object.getImage(),
                object.getColor(),
                object.getBrand(),
                catalog
        );
    }
}
