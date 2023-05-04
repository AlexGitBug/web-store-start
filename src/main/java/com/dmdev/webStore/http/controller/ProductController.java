package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.enums.Role;
import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.ProductFilter;
import com.dmdev.webStore.dto.PageResponse;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
@SessionAttributes({"basket"})
public class ProductController {

    private final ProductService productService;
    private final CatalogService catalogService;
    private final OrderService orderService;
    private final UserService userService;
    private final List<Integer> list;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("product") ProductCreateEditDto product) {
        model.addAttribute("product", product);
        model.addAttribute("colors", Color.values());
        model.addAttribute("brands", Brand.values());
        model.addAttribute("catalogs", catalogService.findAll());
        return "product/registration";
    }

    @GetMapping("/{id}/orders")
    public String findByIdOrders(@PathVariable("id") Integer id,
                                     Model model) {
        model.addAttribute("products", productService.findById(id));
        return "redirect:/orders/registration";
    }

    @GetMapping
    public String findAll(Model model,
                         @Validated ProductFilter filter,
                          BindingResult bindingResult,
                          Pageable pageable,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("filter", filter);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/products";
        }
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
    public String findById(@PathVariable("id") Integer id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        return productService.findById(id)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("products", productService.findAll());
                    model.addAttribute("colors", Color.values());
                    model.addAttribute("brands", Brand.values());
                    model.addAttribute("catalogs", catalogService.findAll());
                    var userById = userService.findById(userService.findByEmail(userDetails.getUsername()).getId());
                    var role = userById.map(UserReadDto::getRole).orElseThrow();
                    model.addAttribute("user", role);
                    return "product/product";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute @Validated ProductCreateEditDto product,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/products/registration";
        }
        return "redirect:/products/" + productService.create(product).getId();
    }
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated ProductCreateEditDto product) {
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

    //    @PostMapping("/{id}/add")
//    public String addToBascket(Model model, @PathVariable("id") Integer id) {
//        list.add(productService.findByProductId(id).getId());
//        return "redirect:/catalogs";
//    }

}
