package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.TupleReadDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartCreateEditDto;
import com.dmdev.webStore.dto.shoppingCart.ShoppingCartReadDto;
import com.dmdev.webStore.mapper.TupleReadMapper;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartCreateEditMapper;
import com.dmdev.webStore.mapper.shoppingCart.ShoppingCartReadMapper;
import com.dmdev.webStore.repository.ShoppingCartRepository;
import com.dmdev.webStore.repository.filter.OrderFilter;
import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartService {

    private final ShoppingCartCreateEditMapper shoppingCartCreateEditMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartReadMapper shoppingCartReadMapper;
    private final TupleReadMapper tupleReadMapper;

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

    public List<ShoppingCartReadDto> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter personalInformationFilter, OrderFilter orderFilter) {
        return shoppingCartRepository.findAllOrdersWithProductsOfOneUser(personalInformationFilter, orderFilter).stream()
                .map(shoppingCartReadMapper::map)
                .collect(toList());
    }

    public List<TupleReadDto> getStatisticOfEachOrdersWithSum() {
        return shoppingCartRepository.getStatisticOfEachOrdersWithSum().stream()
                .map(tupleReadMapper::map)
                .toList();
    }
}