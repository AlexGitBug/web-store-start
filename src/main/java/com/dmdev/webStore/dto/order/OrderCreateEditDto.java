package com.dmdev.webStore.dto.order;

import com.dmdev.webStore.dto.user.UserReadDto;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import com.dmdev.webStore.entity.enums.ProgressStatus;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

@Value
@FieldNameConstants
public class OrderCreateEditDto {

    String city;
    String street;
    Integer building;
    @FutureOrPresent
    LocalDate deliveryDate;
    PaymentCondition paymentCondition;
    ProgressStatus status;
    Integer userId;
}
