package com.dmdev.webStore.dto.order;

import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import com.dmdev.webStore.entity.enums.ProgressStatus;
import lombok.Value;

import java.time.LocalDate;

@Value
public class OrderReadDto {

    Integer id;
    String city;
    String street;
    Integer building;
    LocalDate deliveryDate;
    PaymentCondition paymentCondition;
    ProgressStatus status;
    UserReadDto user;
}
