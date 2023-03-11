package entity;

import entity.enums.Brand;
import entity.enums.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
public class Product {

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

    public String getCategoryAndBrandAndPrice() {
        return getCatalog().getCategory() + " " + getBrand().name() + " " + price;
    }

    public String getCategoryAndBrandAndModel() {
        return getCatalog().getCategory() + " " + getBrand().name() + " " + getModel();
    }

    public String getFullFilterForOneProduct() {
        return getCatalog().getCategory() + " " + getBrand() + " " + getModel() + " " + getDateOfRelease() + " "
                + getPrice() + " " + getColor();
    }
}