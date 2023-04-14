package com.dmdev.webStore.dao.repository.filter;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ProductFilter {

    Integer catalogId;
    Brand brand;
    String model;
    LocalDate dateOfRelease;
    Integer price;
    Integer priceA;
    Integer priceB;
    Color color;

}
