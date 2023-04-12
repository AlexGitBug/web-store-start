package com.dmdev.webStore.dto;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class ProductCreateEditDto {

    String model;
    LocalDate dateOfRelease;
    Integer price;
    String image;
    Color color;
    Brand brand;
    Integer catalogId;
}
