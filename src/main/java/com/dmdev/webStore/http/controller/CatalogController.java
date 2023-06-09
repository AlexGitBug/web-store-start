package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.dto.catalog.CatalogCreateEditDto;
import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.ProductService;
import com.dmdev.webStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final OrderService orderService;

    private final UserService userService;

    @GetMapping("/registration")
    public String registrationCatalog(Model model,
                                      @ModelAttribute("catalog") @Validated CatalogCreateEditDto catalog) {
        model.addAttribute("catalog", catalog);
        model.addAttribute("catalogs", catalogService.findAll());
        return "catalog/registration";
    }

    @GetMapping
    public String findAll(Model model,
                          @AuthenticationPrincipal UserDetails userDetails) {
        var userId = getUserId(userDetails);
        model.addAttribute("catalogs", catalogService.findAll());
        model.addAttribute("userId", userId);
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
    public String findAllProductsByCatalog(@PathVariable("id") Integer id,
                                           Model model,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("products", productService.findAllByCatalogId(id));
        var userId = userService.findByEmail(userDetails.getUsername()).map(UserReadDto::getId).orElseThrow();
        var orderExist = orderService.findByStatusAndUserId(userId)
                .map(OrderReadDto::getId)
                .isPresent();
        model.addAttribute("status", orderExist);
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

    private Integer getUserId(UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername()).map(UserReadDto::getId).orElseThrow();
    }
}

