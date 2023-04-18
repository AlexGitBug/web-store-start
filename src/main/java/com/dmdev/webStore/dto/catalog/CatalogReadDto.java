package com.dmdev.webStore.dto.catalog;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Value
public class CatalogReadDto {

    Integer id;

    @NotBlank
    @NotEmpty
    String category;
}
