package com.dmdev.webStore.service;

import com.dmdev.webStore.dao.repository.CatalogRepository;
import com.dmdev.webStore.dto.CatalogReadDto;
import com.dmdev.webStore.mapper.CatalogReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CatalogReadMapper catalogReadMapper;

    public List<CatalogReadDto> findAll() {
        return catalogRepository.findAll().stream()
                .map(catalogReadMapper::map)
                .collect(toList());
    }
}
