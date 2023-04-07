package com.dmdev.webStore.entity;

import com.dmdev.webStore.entity.embeddable.DeliveryAdress;
import com.dmdev.webStore.entity.enums.PaymentCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "shoppingCarts")
@Builder
@Entity
@Table(name = "orders", schema = "public")
public class Order implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private DeliveryAdress deliveryAdress;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "payment_condition")
    @Enumerated(EnumType.STRING)
    private PaymentCondition paymentCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public void setUser(User user) {
        this.user = user;
        this.user.getOrders().add(this);
    }

    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

}