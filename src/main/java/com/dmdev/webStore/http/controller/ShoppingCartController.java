package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.entity.enums.ProgressStatus;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.ProductService;
import com.dmdev.webStore.service.ShoppingCartService;
import com.dmdev.webStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Controller
@RequestMapping("/shoppingcarts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;


    @GetMapping()
    public String findShoppingCartByOrderId(Model model,
                                            @RequestParam("orderId") Integer orderId) {
        model.addAttribute("shoppingcarts", shoppingCartService.findShoppingCartByOrderId(orderId));
        return "shoppingcart/shoppingcart";
    }


    @PostMapping("/{id}")
    public String create(Model model,
                         @PathVariable("id") Integer id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (orderService.findByStatusAndUserId(userService.findByEmail(userDetails.getUsername()).getId()).getStatus() == ProgressStatus.IN_PROGRESS) {
            OrderReadDto order = orderService.findByStatusAndUserId(userService.findByEmail(userDetails.getUsername()).getId());
            var productId = productService.findByProductId(id).getId();
            shoppingCartService.create(new ShoppingCartCreateEditDto(order.getId(), productId, LocalDate.now()));
        }
//        return "redirect:/orders/" + orderService.findByStatusAndUserId(userService.findByEmail(userDetails.getUsername()).getId()).getId();
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (!shoppingCartService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/orders/" + orderService.findByStatusAndUserId(userService.findByEmail(userDetails.getUsername()).getId()).getId() + "?status=IN_PROGRESS";
    }

}

