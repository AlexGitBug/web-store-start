package com.dmdev.webStore.dto.shoppingCart;

import com.dmdev.webStore.entity.Order;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class ShoppingCartCreateEditDto {

    Integer orderId;
    Integer productId;
    LocalDate createdAt;
    @Min(1L)
    Integer count;
}
