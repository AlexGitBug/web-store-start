package com.dmdev.webStore.service;

import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.repository.ProductRepository;
import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.repository.filter.QPredicate;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.mapper.product.ProductCreateEditMapper;
import com.dmdev.webStore.mapper.product.ProductReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.dmdev.webStore.entity.QCatalog.catalog;
import static com.dmdev.webStore.entity.QProduct.product;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductReadMapper productReadMapper;
    private final ProductCreateEditMapper productCreateEditMapper;
    private final ImageService imageService;

    public Page<ProductReadDto> findAllProducts(ProductFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getCatalogId(), catalog.id::eq)
                .add(filter.getBrand(), product.brand::eq)
                .add(filter.getPriceMin(), product.price::gt)
                .add(filter.getPriceMax(), product.price::lt)
                .buildAnd();

        return productRepository.findAll(predicate, pageable)
                .map(productReadMapper::map);

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
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return productCreateEditMapper.map(dto);
                })
                .map(productRepository::save)
                .map(productReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<ProductReadDto> update(Integer id, ProductCreateEditDto productDto) {
        return productRepository.findById(id)
                .map(entity -> {
                    uploadImage(productDto.getImage());
                    return productCreateEditMapper.map(productDto, entity);
                })
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


    public List<ProductReadDto> findAllProductsFromOrder(OrderFilter orderFilter) {
        return productRepository.findAllProductsFromOrder(orderFilter).stream()
                .map(productReadMapper::map)
                .toList();
    }

    public List<ProductReadDto> findAllByCatalogId(Integer id) {
        return productRepository.findAllByCatalogId(id)
                .stream().map(productReadMapper::map)
                .toList();
    }



    public ProductReadDto findByProductId(Integer id) {
        return productRepository.findById(id)
                .map(productReadMapper::map)
                .orElseThrow();
    }
    public Optional<byte[]> findImage(Integer id) {
        return productRepository.findById(id)
                .map(Product::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
    //-------------------------------------------------------------------------

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }




}
























