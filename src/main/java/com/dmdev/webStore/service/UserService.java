package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.user.UserCreateEditDto;
import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.mapper.user.UserCreateEditMapper;
import com.dmdev.webStore.mapper.user.UserReadMapper;
import com.dmdev.webStore.repository.UserRepository;
import com.dmdev.webStore.repository.filter.UserFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateEditMapper userCreateEditMapper;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::map)
                .toList();
    }
    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::map);
    }

    public List<UserReadDto> findUsersWhoMadeOrderSpecificTime(UserFilter filter) {
        return userRepository.findUsersWhoMadeOrderSpecificTime(filter).stream()
                .map(userReadMapper::map)
                .toList();
    }
    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditMapper::map)
                .map(userRepository::save)
                .map(userReadMapper::map)
                .orElseThrow();
    }
    @Transactional
    public Optional<UserReadDto> update(Integer id, UserCreateEditDto userDto) {
        return userRepository.findById(id)
                .map(entity -> userCreateEditMapper.map(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userReadMapper::map);
    }
    @Transactional
    public boolean delete(Integer id) {
        return userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}