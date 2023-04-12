package com.dmdev.webStore.entity;

import com.dmdev.webStore.entity.enums.Brand;
import com.dmdev.webStore.entity.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"shoppingCarts", "catalog"})
@Builder
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "product", schema = "public")
public class Product implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @Enumerated(EnumType.STRING)
    private Brand brand;
    private String model;
    private LocalDate dateOfRelease;
    private Integer price;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "image")
    private String image;

    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

}