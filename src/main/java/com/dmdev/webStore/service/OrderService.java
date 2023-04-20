package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.product.ProductCreateEditDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.mapper.order.OrderCreateEditMapper;
import com.dmdev.webStore.mapper.order.OrderReadMapper;
import com.dmdev.webStore.repository.OrderRepository;
import com.dmdev.webStore.repository.filter.PersonalInformationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;


    public List<OrderReadDto> findAllOrdersWithProductsOfOneUser(PersonalInformationFilter filter) {
        return orderRepository.findAllOrdersWithProductsOfOneUser(filter).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAll(Sort sort) {
        return orderRepository.findAll(sort).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }
    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Integer id, OrderCreateEditDto orderDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
