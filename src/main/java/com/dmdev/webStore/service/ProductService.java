package com.dmdev.webStore.service;

import com.dmdev.webStore.dao.repository.ProductRepository;
import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dao.repository.filter.QPredicate;
import com.dmdev.webStore.dto.ProductCreateEditDto;
import com.dmdev.webStore.dto.ProductReadDto;
import com.dmdev.webStore.mapper.ProductCreateEditMapper;
import com.dmdev.webStore.mapper.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QProduct.product;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateEditMapper productCreateEditMapper;

    public Page<ProductReadDto> findListOfProductsEq(ProductFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getCatalogId(), catalog.id::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPriceA(), product.price::gt)
                .add(filter.getPriceB(), product.price::lt)
                .buildAnd();

       return productRepository.findAll(predicate, pageable)
                .map(productReadMapper::map);


//        return productRepository.findListOfProductsEq(filter).stream()
//                .map(productReadMapper::map)
//                .toList();
    }
    public List<ProductReadDto> findAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    public Optional<ProductReadDto> findById(Integer id) {
        return productRepository.findById(id)
                .map(productReadMapper::map);
    }

    @Transactional
    public ProductReadDto create(ProductCreateEditDto productDto) {
        return Optional.of(productDto)
                .map(productCreateEditMapper::map)
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Integer id, ProductCreateEditDto productDto) {
        return productRepository.findById(id)
                .map(entity -> productCreateEditMapper.map(productDto, entity))
                .map(productRepository::saveAndFlush)
                .map(productReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return productRepository.findById(id)
                .map(entity -> {
                    productRepository.delete(entity);
                    productRepository.flush();
                    return true;
                })
                .orElse(false);
    }


}
























