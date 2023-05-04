package com.dmdev.webStore.dto.product;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import com.dmdev.webStore.validation.ProductInfo;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Value
@FieldNameConstants
@ProductInfo
public class ProductCreateEditDto {

    String model;
    @PastOrPresent
    LocalDate dateOfRelease;
    @Positive
    Integer price;
    Color color;
    Brand brand;
    Integer catalogId;

    MultipartFile image;
}


