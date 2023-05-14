package com.dmdev.webStore.http.controller;

import com.dmdev.webStore.dto.CountDto;
import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartReadDto;
import com.dmdev.webStore.dto.user.UserReadDto;
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

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

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
        model.addAttribute("userid", getUserId(userDetails));
        model.addAttribute("status", values());
        return "order/registration";
    }

    @GetMapping("/getstatisticofeachorderswithsum")
    public String getStatisticOfEachOrdersWithSum(Model model) {
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
                                                     PersonalInformationFilter personalFilter,
                                                     OrderFilter orderFilter) {
        model.addAttribute("shoppingcarts",
                shoppingCartService.findAllOrdersWithProductsOfOneUser(personalFilter, orderFilter));
        model.addAttribute("personalFilter", personalFilter);
        model.addAttribute("orderFilter", orderFilter);
        return "order/allordersofoneuser";
    }

    @GetMapping("/allordersbyuserid")
    public String findAllOrdersWithProductsByUserId(Model model,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("orders",
                orderService.findAllByUserId(getUserId(userDetails)));
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
    public String saveShoppingCard(Model model,
                                   @PathVariable("id") Integer id,
                                   @AuthenticationPrincipal UserDetails userDetails
    ) {
        var userId = getUserId(userDetails);
        var orderId = orderService.findByUserId(userId).map(OrderReadDto::getId).orElseThrow();
        var productId = productService.findByProductId(id).getId();
        shoppingCartService.create(new ShoppingCartCreateEditDto(orderId, productId, LocalDate.now(), null));
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
//                           @RequestParam("status") ProgressStatus status,
                           Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        var userId = getUserId(userDetails);
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("payments", PaymentCondition.values());
                    model.addAttribute("users", userService.findAll());
                    model.addAttribute("catalogs", catalogService.findAll());
                    model.addAttribute("userid", userId);
                    model.addAttribute("status", values());
                    model.addAttribute("shoppingcarts", shoppingCartService.findShoppingCartByOrderId(id));
                    model.addAttribute("statistics", shoppingCartService.getStatisticSumOfOrder(id));
                    return "order/order";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/afterloginpage")
    public String afterLoginPageContinue(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("user", userService.findByEmail(userDetails.getUsername()));
        var userId = getUserId(userDetails);
        var order = orderService.findByStatusAndUserId(userId);
        return order.map(orderReadDto -> "redirect:/orders/" + orderReadDto.getId() + "?status=IN_PROGRESS")
                .orElse("redirect:/orders/reg");
    }


    @PostMapping
    public String create(@ModelAttribute @Validated OrderCreateEditDto order) {
        var orderReadDto = orderService.create(order);
        return orderReadDto.map(it -> "redirect:/orders/" + it.getId() + "?status=IN_PROGRESS")
                .orElse("redirect:/orders/reg");
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute OrderCreateEditDto order,
                         @RequestParam("status") ProgressStatus status) {
        return orderService.update(id, order)
                .map(it -> "redirect:/orders/{id}" + "?status=IN_PROGRESS")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/setStatus")
    public String setStatus(@PathVariable("id") Integer id,
                           @SessionAttribute("basket") Map<Integer, Integer> productIdAndCount) {
        orderService.setStatus(id);
        productIdAndCount.clear();
        return "redirect:/catalogs";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!orderService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/products";
    }


    @GetMapping("/reg")
    public String registration2(Model model,
                                @ModelAttribute("order") @Validated OrderCreateEditDto order,
                                @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("order", order);
        model.addAttribute("payments", PaymentCondition.values());
        model.addAttribute("userid", getUserId(userDetails));
        model.addAttribute("status", values());
        return "order/reg";
    }

    @PostMapping("/create")
    public String create2(@ModelAttribute @Validated OrderCreateEditDto order,
                          @AuthenticationPrincipal UserDetails userDetails,
                          @SessionAttribute("basket") Map<Integer, Integer> productIdAndCount) {
        var orderReadDto = orderService.create(order);
        var userId = getUserId(userDetails);
        var orderId = orderService.findByStatusAndUserId(userId).map(OrderReadDto::getId).orElseThrow();
        var shoppingCartList = shoppingCartService.findShoppingCartByOrderId(orderId);
        if (shoppingCartList.isEmpty()) {
            for (Map.Entry<Integer, Integer> integerEntry : productIdAndCount.entrySet()) {
                var productId = integerEntry.getKey();
                var count = integerEntry.getValue();
                var shoppingCartReadDto = shoppingCartService.create(new ShoppingCartCreateEditDto(orderId, productId, LocalDate.now(), count));
            }
            return orderReadDto.map(it -> "redirect:/orders/" + it.getId() + "?status=IN_PROGRESS").orElse("redirect:/orders/reg");
        }
        return "redirect:/products";
    }

    private Integer getUserId(UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername())
                .map(UserReadDto::getId)
                .orElseThrow();
    }
    }

