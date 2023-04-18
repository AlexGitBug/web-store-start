package com.dmdev.webStore.mapper.product;

import com.dmdev.webStore.mapper.Mapper;
import com.dmdev.webStore.repository.CatalogRepository;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.Catalog;
import com.dmdev.webStore.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductCreateEditMapper implements Mapper<ProductCreateEditDto, Product> {

    private final CatalogRepository catalogRepository;

    @Override
    public Product map(ProductCreateEditDto fromObject, Product toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Product map(ProductCreateEditDto object) {
        Product product = new Product();
        copy(object, product);

        return product;
    }

    private void copy(ProductCreateEditDto object, Product product) {
        product.setModel(object.getModel());
        product.setDateOfRelease(object.getDateOfRelease());
        product.setPrice(object.getPrice());
        product.setImage(object.getImage());
        product.setColor(object.getColor());
        product.setBrand(object.getBrand());
        product.setCatalog(getCatalog(object.getCatalogId()));
    }

    private Catalog getCatalog(Integer catalogId) {
        return Optional.ofNullable(catalogId)
                .flatMap(id -> catalogRepository.findById(id))
                .orElse(null);
    }
}
