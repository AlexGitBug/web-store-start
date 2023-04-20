package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.product.ProductCreateEditDto;

import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.ProductService;
import com.dmdev.webStore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shoppingcarts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    private final OrderService orderService;

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("product") @Validated ShoppingCartCreateEditDto shoppingCartDto) {
        model.addAttribute("shoppingCart", shoppingCartDto);
        model.addAttribute("products", productService.findAll());
        model.addAttribute("orders", orderService.findAll());
        return "shoppingcart/registration";
    }

    @PostMapping
    public String create(@ModelAttribute ShoppingCartCreateEditDto shoppingCart, RedirectAttributes redirectAttributes) {
        return "redirect:/shoppingcarts/" + shoppingCartService.create(shoppingCart).getId();
    }
}
