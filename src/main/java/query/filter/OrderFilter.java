package query.filter;

import entity.embeddable.DeliveryAdress;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class OrderFilter {

    DeliveryAdress deliveryAdress;
    LocalDate deliveryDate;
}