package com.dmdev.webStore.repository.filter;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Value
@Builder
public class ProductFilter {

    Integer catalogId;
    Brand brand;
    String model;
    LocalDate dateOfRelease;
    @PositiveOrZero
    Integer price;
    @PositiveOrZero
    Integer priceMin;
    @PositiveOrZero
    @Min(value = 0)
    Integer priceMax;
    Integer priceLow;
    Color color;

}
