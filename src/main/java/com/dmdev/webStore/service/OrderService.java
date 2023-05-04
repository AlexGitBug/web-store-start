package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.order.OrderCreateEditDto;
import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.entity.enums.ProgressStatus;
import com.dmdev.webStore.mapper.order.OrderCreateEditMapper;
import com.dmdev.webStore.mapper.order.OrderReadMapper;
import com.dmdev.webStore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.dmdev.webStore.entity.enums.ProgressStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .toList();
    }
    public Optional<OrderReadDto> findByUserId(Integer id) {
        return orderRepository.findByUserId(id)
                .map(orderReadMapper::map);
    }

    public Optional<OrderReadDto> findByIdAndStatus(Integer id, ProgressStatus status) {
        return orderRepository.findByIdAndStatus(id, status)
                .map(orderReadMapper::map);
    }

    public Optional<OrderReadDto> findByStatusAndUserId(Integer id) {
        return orderRepository.findByStatusAndUserId(IN_PROGRESS, id)
                .map(orderReadMapper::map);
    }

    public List<OrderReadDto> findAllByUserId(Integer id) {
        return orderRepository.findAllByUserId(id).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    public List<OrderReadDto> findAll(Sort sort) {
        return orderRepository.findAll(sort).stream()
                .map(orderReadMapper::map)
                .toList();
    }

    @Transactional
    public Optional<OrderReadDto> create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map);
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

    @Transactional
    public int setStatus(Integer id) {
        return orderRepository.setStatus(PAID, id);
    }


}
