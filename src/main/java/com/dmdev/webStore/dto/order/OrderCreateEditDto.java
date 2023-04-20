package com.dmdev.webStore.dto.order;

import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    String city;
    String street;
    Integer building;
    LocalDate deliveryDate;
    PaymentCondition paymentCondition;
    Integer userId;
}
