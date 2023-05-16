package com.dmdev.webStore.service;

import com.dmdev.webStore.dto.catalog.CatalogCreateEditDto;
import com.dmdev.webStore.mapper.catalog.CatalogCreateEditMapper;
import com.dmdev.webStore.repository.CatalogRepository;
import com.dmdev.webStore.dto.catalog.CatalogReadDto;
import com.dmdev.webStore.mapper.catalog.CatalogReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;
    private final CatalogReadMapper catalogReadMapper;
    private final CatalogCreateEditMapper catalogCreateEditMapper;

    public List<CatalogReadDto> findAll() {
        return catalogRepository.findAll().stream()
                .map(catalogReadMapper::map)
                .collect(toList());
    }
    public Optional<CatalogReadDto> findById(Integer id) {
        return catalogRepository.findById(id)
                .map(catalogReadMapper::map);
    }

    @Transactional
    public CatalogReadDto create(CatalogCreateEditDto dto) {
        return Optional.of(dto)
                .map(catalogCreateEditMapper::map)
                .map(catalogRepository::save)
                .map(catalogReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CatalogReadDto> update(Integer id, CatalogCreateEditDto dto) {
        return catalogRepository.findById(id)
                .map(entity -> catalogCreateEditMapper.map(dto, entity))
                .map(catalogRepository::saveAndFlush)
                .map(catalogReadMapper::map);
    }

    @Transactional()
    public boolean delete(Integer id) {
        return catalogRepository.findById(id)
                .map(entity -> {
                    catalogRepository.delete(entity);
                    catalogRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
