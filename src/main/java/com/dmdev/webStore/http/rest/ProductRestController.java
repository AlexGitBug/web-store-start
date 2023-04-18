package com.dmdev.webStore.http.rest;


import com.dmdev.webStore.dto.PageResponse;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;
    private final CatalogService catalogService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<ProductReadDto> findAll(ProductFilter filter, Pageable pageable) {
        var page = productService.findAllProducts(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public ProductReadDto findById(@PathVariable("id") Integer id) {
        return productService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReadDto create(@RequestBody ProductCreateEditDto product) {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ProductReadDto update(@PathVariable("id") Integer id, @RequestBody ProductCreateEditDto product) {
        return productService.update(id, product)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void delete(@PathVariable("id") Integer id) {
        if (!productService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
