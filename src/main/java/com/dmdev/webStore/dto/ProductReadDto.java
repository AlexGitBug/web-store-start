package com.dmdev.webStore.dto;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ProductReadDto {

    Integer id;
    String model;
    LocalDate dateOfRelease;
    Integer price;
    String image;
    Color color;
    Brand brand;
    CatalogReadDto catalog;
}
