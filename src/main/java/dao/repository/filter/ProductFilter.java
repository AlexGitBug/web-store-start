package dao.repository.filter;

import entity.Catalog;
import entity.enums.Brand;
import entity.enums.Color;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ProductFilter {

    Catalog catalog;
    Brand brand;
    String model;
    LocalDate dateOfRelease;
    Integer price;
    Color color;

}
