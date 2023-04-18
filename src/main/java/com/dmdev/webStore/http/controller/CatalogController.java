package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;
    private final ProductService productService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("catalogs", catalogService.findAll());
        return "catalog/catalogs";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return catalogService.findById(id)
                .map(catalog -> {
                    model.addAttribute("catalog", catalog);
                    model.addAttribute("products", productService.findAll());
                    return "catalog/catalog";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

