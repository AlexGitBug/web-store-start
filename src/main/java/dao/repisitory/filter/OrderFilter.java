package dao.repisitory.filter;

import entity.embeddable.DeliveryAdress;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class OrderFilter {
    Integer id;
    DeliveryAdress deliveryAdress;
    LocalDate deliveryDate;
}