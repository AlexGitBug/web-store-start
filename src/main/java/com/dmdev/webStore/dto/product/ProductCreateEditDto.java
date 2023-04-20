package com.dmdev.webStore.dto.product;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class ProductCreateEditDto {

    String model;
    LocalDate dateOfRelease;
    Integer price;
//    String image;
    Color color;
    Brand brand;
    Integer catalogId;

    MultipartFile image;
}


