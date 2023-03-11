package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Arrays;

import static util.StringUtil.SPACE;

@NamedEntityGraph(name = "findAllOrdersOfUsers",
        attributeNodes = {
                @NamedAttributeNode(value = "order", subgraph = "user"),
                @NamedAttributeNode(value = "product", subgraph = "catalog")},
        subgraphs = {
                @NamedSubgraph(name = "catalog", attributeNodes = @NamedAttributeNode("catalog")),
                @NamedSubgraph(name = "user", attributeNodes = @NamedAttributeNode("user"))
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public void setOrder(Order order) {
        this.order = order;
        this.order.getShoppingCarts().add(this);
    }

    public void setProduct(Product product) {
        this.product = product;
        this.product.getShoppingCarts().add(this);
    }

    public void addProduct(Session session, Order order, Product... products) {
        Arrays.stream(products)
                .map(product -> ShoppingCart.builder()
                        .order(order)
                        .product(product)
                        .createdAt(LocalDate.now())
                        .build())
                .forEach(session::save);
    }

    public String getIdAndCatalogOfProduct() {
        return order.getId() + " " + product.getCatalog().getCategory() + SPACE + product.getModel();
    }

    public String getUserIdAndOrderIdAndLocalDateOfOrder() {
        return order.getUser().getPersonalInformation().getLastName() + SPACE + order.getUser().getPersonalInformation().getEmail() + SPACE +
                order.getId() + SPACE + getCreatedAt();
    }

    public String getUsersNameAndMailAndSpecificProduct() {
        return order.getUser().getPersonalInformation().getFirstName() + SPACE + order.getUser().getPersonalInformation().getTelephone() + SPACE + order.getUser().getPersonalInformation().getEmail() + SPACE +
                getProduct().getCategoryAndBrandAndModel();

    }
}