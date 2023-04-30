package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.enums.PaymentCondition;

import com.dmdev.webStore.entity.enums.ProgressStatus;
import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.dmdev.webStore.service.CatalogService;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.ProductService;
import com.dmdev.webStore.service.ShoppingCartService;
import com.dmdev.webStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static com.dmdev.webStore.entity.enums.ProgressStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CatalogService catalogService;
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("order") @Validated OrderCreateEditDto order,
                               @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("order", order);
        model.addAttribute("payments", PaymentCondition.values());
        model.addAttribute("userid", userService.findByEmail(userDetails.getUsername()).getId());
        model.addAttribute("status", values());
        return "order/registration";
    }

    @GetMapping("/getstatisticofeachorderswithsum")
    public String getStatisticOfEachOrdersWithSum(Model model){
        model.addAttribute("statistics", shoppingCartService.getStatisticOfEachOrdersWithSum());
        return "order/getstatisticofeachorderswithsum";
    }

    @GetMapping
    public String findAll(Model model, @ModelAttribute OrderCreateEditDto order) {
        var sortBy = Sort.sort(Order.class);
        var sort = sortBy.by(Order::getDeliveryDate);
        model.addAttribute("orders", orderService.findAll(sort));
        return "order/orders";
    }

    @GetMapping("/allordersofoneuser")
    public String findAllOrdersWithProductsOfOneUser(Model model,
                                                     PersonalInformationFilter filter) {
        model.addAttribute("shoppingcarts", shoppingCartService.findAllOrdersWithProductsOfOneUser(filter));
        return "order/allordersofoneuser";
    }

    @GetMapping("/allordersbyuserid")
    public String findAllOrdersWithProductsByUserId(Model model,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders", orderService.findAllByUserId(userService.findByEmail(userDetails.getUsername()).getId()));
        model.addAttribute("users", userService.findAll());
        return "order/allordersbyuserid";
    }

    @GetMapping("/{id}/onecatalog")
    public String findAllProductsByCatalog(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("products", productService.findAllByCatalogId(id));
        return "catalog/onecatalog";
    }

    @GetMapping("/productsfromorder")
    public String findAllProductsFromOrder(Model model, OrderFilter filter) {
        model.addAttribute("products", productService.findAllProductsFromOrder(filter));
        return "catalog/onecatalog";
    }

    @PostMapping("/{id}/add")
    public String create(Model model,
                         @PathVariable("id") Integer id,
                         @AuthenticationPrincipal UserDetails userDetails
    ) {
        var userId = userService.findByEmail(userDetails.getUsername()).getId();
        var orderId = orderService.findByUserId(userId).getId();
        var productId = productService.findByProductId(id).getId();
        shoppingCartService.create(new ShoppingCartCreateEditDto(orderId, productId, LocalDate.now()));
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           @RequestParam("status") ProgressStatus status,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        if (status == IN_PROGRESS) {
            return getModelForFindById(id, model, userDetails, IN_PROGRESS);
        }
        return getModelForFindById(id, model, userDetails, PAID);
    }

    @PostMapping
    public String create(@ModelAttribute OrderCreateEditDto order, RedirectAttributes redirectAttributes) {
        orderService.create(order);
        return "redirect:/catalogs";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute OrderCreateEditDto order,
                         @RequestParam("status") ProgressStatus status) {
        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/setStatus")
    public String setStatus(@PathVariable("id") Integer id, @ModelAttribute OrderCreateEditDto order) {
        orderService.setStatus(id);
        return "redirect:/catalogs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/orders";
    }

    private String getModelForFindById(@PathVariable("id") Integer id, Model model,
                                       @AuthenticationPrincipal UserDetails userDetails,
                                       ProgressStatus paid) {
        return orderService.findByIdAndStatus(id, paid)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("payments", PaymentCondition.values());
                    model.addAttribute("users", userService.findAll());
                    model.addAttribute("catalogs", catalogService.findAll());
                    model.addAttribute("userid", userService.findByEmail(userDetails.getUsername()).getId());
                    model.addAttribute("status", values());
                    model.addAttribute("shoppingcarts", shoppingCartService.findShoppingCartByOrderId(
                            orderService.findByIdAndStatus(id, paid).get().getId()));
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


}

