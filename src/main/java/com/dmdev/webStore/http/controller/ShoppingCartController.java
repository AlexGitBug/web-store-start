package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.ProductService;
import com.dmdev.webStore.service.ShoppingCartService;
import com.dmdev.webStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String findShoppingCartByOrderId(Model model, @RequestParam("orderId") Integer orderId) {
        model.addAttribute("shoppingcarts", shoppingCartService.findShoppingCartByOrderId(orderId));
        return "shoppingcart/shoppingcart";
    }


    @PostMapping("/{id}")
    public String create(Model model,
                         @PathVariable("id") Integer id,
                         @AuthenticationPrincipal UserDetails userDetails
    ) {
        var userId = userService.findByEmail(userDetails.getUsername()).getId();
        var orderId = orderService.findByUserId(userId).getId();
        var productId = productService.finByProductId(id).getId();
        shoppingCartService.create(new ShoppingCartCreateEditDto(orderId, productId, LocalDate.now()));
        return "redirect:/products";
    }

//    @PostMapping("/{id}")
//    public String create(Model model,
//                         @PathVariable("id") Integer id,
//                         @AuthenticationPrincipal UserDetails userDetails
////                         @RequestParam("orderId") Integer orderId
//    ) {
////        model.addAttribute("products", productService.findById(productId));
////        model.addAttribute("orders", orderService.findById(orderId));
//        var productId = productService.findById(id).get().getId();
//        var orderIdFromUser = userService.findById(1).get().getId();
//        shoppingCartService.create(new ShoppingCartCreateEditDto(orderIdFromUser, productId, LocalDate.now()));
//        return "redirect:/products";
//    }


//    @GetMapping("/registration")
//    public String registration(Model model, @Validated ShoppingCartCreateEditDto shoppingcart) {
//        model.addAttribute("shoppingcart", shoppingcart);
//        model.addAttribute("products", productService.findAll());
//        model.addAttribute("orders", orderService.findAll());
//        return "shoppingcart/registration";
//    }

//    @PostMapping
//    public String create(@ModelAttribute ShoppingCartCreateEditDto shoppingCart, RedirectAttributes redirectAttributes) {
//        return "redirect:/shoppingcarts/" + shoppingCartService.create(shoppingCart).getId();
//    }

//    @PostMapping("/{id}")
//    public String addProductToShoppingCart(@PathVariable("id") Integer id, @RequestParam("order_id") Integer orderId, Model model) {
//        var currentUser = userService.findById(1);
//        model.addAttribute("")// добавить продукт по Id в таблицу корзины
//        model.addAttribute("")// добавить order_id по Id в таблицу корзины
//        // сг
//    }

//    @PostMapping("/save")
//    public String addProductAndOrderToShoppingCart(@RequestParam("productId") Integer productId,
//                                                   @RequestParam("orderId") Integer orderId,
//                                                   Model model){
//        model.addAttribute("save", shoppingCartService.create(sorderId, productId));
//        return "product/products";
//    }

//    @PostMapping()
//    public String registration(Model model,
//                         @RequestParam("productId") Integer productId,
//                         @RequestParam("orderId") Integer orderId) {
//        shoppingCartService.create(new ShoppingCartCreateEditDto(orderId, productId, LocalDate.now()));
//        return "redirect:product/products";
//    }



}

