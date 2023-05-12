package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.CountDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
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
                         @AuthenticationPrincipal UserDetails userDetails,
                         Integer count) {
        model.addAttribute("count", count);
        var userId = userService.findByEmail(userDetails.getUsername()).map(UserReadDto::getId).orElseThrow();
        var order = orderService.findByStatusAndUserId(userId);
        order.ifPresent(orderReadDto -> shoppingCartService
                .create(new ShoppingCartCreateEditDto(orderReadDto.getId(), id, LocalDate.now(), count)));
        return order.map(orderReadDto -> "redirect:/orders/" + orderReadDto.getId() + "?status=IN_PROGRESS")
                .orElse("redirect:error/error500");

    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        if (!shoppingCartService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        var userId = userService.findByEmail(userDetails.getUsername()).map(UserReadDto::getId).orElseThrow();
        var order = orderService.findByStatusAndUserId(userId);
        return order.map(orderReadDto -> "redirect:/orders/" + orderReadDto.getId() + "?status=IN_PROGRESS")
                .orElse("redirect:/orders/registration");
    }
}

