package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartReadDto;
import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartCreateEditMapper;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartReadMapper;
import com.dmdev.webStore.repository.ShoppingCartRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartService {

    private final ShoppingCartCreateEditMapper shoppingCartCreateEditMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartReadMapper shoppingCartReadMapper;

    @Transactional
    public ShoppingCartReadDto create(ShoppingCartCreateEditDto shoppingCartCreateEditDto) {
        return Optional.of(shoppingCartCreateEditDto)
                .map(shoppingCartCreateEditMapper::map)
                .map(shoppingCartRepository::save)
                .map(shoppingCartReadMapper::map)
                .orElseThrow();
    }

//    @Transactional
//    public List<Tuple> getStatisticOfEachOrdersWithSum() {
//        return
//    }

}
