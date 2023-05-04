package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.dto.catalog.CatalogCreateEditDto;
import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;
    private final ProductService productService;

    @GetMapping("/registration")
    public String registrationCatalog(Model model,
                                      @ModelAttribute("catalog") @Validated CatalogCreateEditDto catalog) {
        model.addAttribute("catalog", catalog);
        model.addAttribute("catalogs", catalogService.findAll());
        return "catalog/registration";
    }

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


    @GetMapping("/{id}/onecatalog")
    public String findAllProductsByCatalog(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("products", productService.findAllByCatalogId(id));
        return "catalog/onecatalog";
    }
    @PostMapping
    public String create(@ModelAttribute CatalogCreateEditDto catalog) {
        catalogService.create(catalog);
        return "redirect:/catalogs";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute CatalogCreateEditDto catalog) {
        return catalogService.update(id, catalog)
                .map(it -> "redirect:/catalogs/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!catalogService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/catalogs";
    }
}

