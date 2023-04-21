package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.entity.Product;
import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.dto.PageResponse;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CatalogService catalogService;
    private final OrderService orderService;

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("product") @Validated ProductCreateEditDto product) {
        model.addAttribute("product", product);
        model.addAttribute("colors", Color.values());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("catalogs", catalogService.findAll());
        return "product/registration";
    }

    @GetMapping
    public String findAll(Model model, ProductFilter filter, Pageable pageable) {
        var page = productService.findAllProducts(filter, pageable);
        model.addAttribute("products", PageResponse.of(page));
        model.addAttribute("filter", filter);
        model.addAttribute("brands", Brand.values());
        model.addAttribute("catalogs", catalogService.findAll());
        model.addAttribute("orders", orderService.findAll());
        return "product/products";
    }
    @GetMapping("/{id}/onecatalog")
    public String findAllProductsByCatalog(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("products", productService.findAllByCatalogId(id));
        return "catalog/onecatalog";
    }

    @GetMapping("/productsfromorder")
    public String findAllProductsFromOrder(Model model, OrderFilter filter) {
        model.addAttribute("filter", filter);
        model.addAttribute("products", productService.findAllProductsFromOrder(filter));
        return "product/productsfromorder";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("products", productService.findAll());
                    model.addAttribute("colors", Color.values());
                    model.addAttribute("brands", Brand.values());
                    model.addAttribute("catalogs", catalogService.findAll());
                    return "product/product";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute ProductCreateEditDto product, RedirectAttributes redirectAttributes) {
        return "redirect:/products/" + productService.create(product).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute ProductCreateEditDto product) {
        return productService.update(id, product)
                .map(it -> "redirect:/products/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!productService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products";
    }


}
