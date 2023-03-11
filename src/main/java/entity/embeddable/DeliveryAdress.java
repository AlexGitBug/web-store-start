package entity.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class DeliveryAdress {

    @Column(name = "delivery_city")
    private String city;
    @Column(name = "delivery_street")
    private String street;
    @Column(name = "delivery_building")
    private Integer building;

}