package com.dmdev.webStore.dao.repository.filter;

import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import lombok.Builder;
import lombok.Value;
import org.hibernate.annotations.Filter;


import java.time.LocalDate;

@Value
@Builder
@Filter(name = "OrderFilter")
public class OrderFilter {
    Integer id;
    DeliveryAdress deliveryAdress;
    LocalDate deliveryDate;
}