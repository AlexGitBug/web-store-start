package com.dmdev.webStore.http.controller;


import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.User;
import com.dmdev.webStore.entity.enums.Role;
import com.dmdev.webStore.repository.filter.UserFilter;
import com.dmdev.webStore.service.OrderService;
import com.dmdev.webStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @GetMapping("/oldorneworder")
    public String oldorneworder(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var user = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        var userId = userService.findByEmail(userDetails.getUsername()).map(UserReadDto::getId).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("status", orderService.findByStatusAndUserId(userId));
        return "user/oldorneworder";
    }

//    @GetMapping("/ordertime")
//    public String findUsersWhoMadeOrderSpecificTime(Model model, UserFilter filter) {
//        model.addAttribute("users", userService.findUsersWhoMadeOrderSpecificTime(filter));
//        return "user/ordertime";
//    }

    @GetMapping
    public String findAll(Model model) {
        var sort = Sort.sort(User.class);
        var sortUser = sort.by((User user) -> user.getPersonalInformation().getFirstName()).ascending();
        model.addAttribute("users", userService.findAllSort(sortUser));
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        var role = userService.findByEmail(userDetails.getUsername()).map(UserReadDto::getRole).orElseThrow();
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("users", userService.findAll());
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("userRole", role);
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute @Validated UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        userService.create(user);
        return "redirect:/login";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated UserCreateEditDto userDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }
        return userService.update(id, userDto)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}