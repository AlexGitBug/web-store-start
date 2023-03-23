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
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@NamedEntityGraph(name = "withCatalog", attributeNodes = {
        @NamedAttributeNode(value = "catalog")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"shoppingCarts", "catalog"})
@Builder
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) // Second Level Cache
public class Product implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    private Integer count;

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