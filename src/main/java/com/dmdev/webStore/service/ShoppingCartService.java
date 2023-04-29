package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartReadDto;
import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartCreateEditMapper;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartReadMapper;
import com.dmdev.webStore.repository.OrderRepository;
import com.dmdev.webStore.repository.ProductRepository;
import com.dmdev.webStore.repository.ShoppingCartRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartService {

    private final ShoppingCartCreateEditMapper shoppingCartCreateEditMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;
    private final ShoppingCartReadMapper shoppingCartReadMapper;

    @Transactional
    public ShoppingCartReadDto create(ShoppingCartCreateEditDto shoppingCartCreateEditDto) {
        return Optional.of(shoppingCartCreateEditDto)
                .map(shoppingCartCreateEditMapper::map)
                .map(shoppingCartRepository::save)
                .map(shoppingCartReadMapper::map)
                .orElseThrow();
    }

    public List<ShoppingCartReadDto> findShoppingCartByOrderId(Integer orderId) {
        return shoppingCartRepository.findShoppingCartByOrderId(orderId).stream()
                .map(shoppingCartReadMapper::map)
                .collect(toList());
    }

    public List<ShoppingCartReadDto> findAll() {
        return shoppingCartRepository.findAll().stream()
                .map(shoppingCartReadMapper::map)
                .collect(toList());
    }

    @Transactional
    public boolean delete(Integer id) {
        return shoppingCartRepository.findById(id)
                .map(entity -> {
                    shoppingCartRepository.delete(entity);
                    shoppingCartRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}