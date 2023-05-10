package com.dmdev.webStore.dto.shoppingCart;

import com.dmdev.webStore.dto.order.OrderReadDto;
import com.dmdev.webStore.dto.product.ProductReadDto;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class ShoppingCartReadDto {

    Integer id;
    OrderReadDto order;
    ProductReadDto product;
    LocalDate createdAt;
    Integer count;
}
