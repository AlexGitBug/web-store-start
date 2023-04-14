package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.dao.repository.filter.ProductFilter;
import com.dmdev.webStore.dto.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CatalogService catalogService;

    @GetMapping("/registrationproduct")
    public String registrationProduct(Model model, @ModelAttribute("product") ProductCreateEditDto product) {
        model.addAttribute("product", product);
        model.addAttribute("colors", Color.values());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("catalogs", catalogService.findAll());
        return "product/registrationproduct";
    }

    @GetMapping
    public String findAll(Model model, ProductFilter filter) {
        model.addAttribute("products", productService.findAll(filter));
        model.addAttribute("brands", Brand.values());
        model.addAttribute("catalogs", catalogService.findAll());
        return "product/products";
    }
//    @GetMapping
//    public String findAll(Model model) {
//        model.addAttribute("products", productService.findAll());
//        model.addAttribute("brands", Brand.values());
//        return "product/products";
//    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("colors", Color.values());
                    model.addAttribute("brands", Brand.values());
                    model.addAttribute("catalogs", catalogService.findAll());
                    return "product/product";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute ProductCreateEditDto product, RedirectAttributes redirectAttributes) {
//        if(true){
//            redirectAttributes.addFlashAttribute("product", product);
//            return "redirect:/products/registrationProduct";
//        }
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
