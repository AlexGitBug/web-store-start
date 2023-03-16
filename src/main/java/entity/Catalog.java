package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Catalog implements BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String category;

    @Builder.Default
    @OneToMany(mappedBy = "catalog")
//    @JoinColumn(name = "catalog_id")
    List<Product> products = new ArrayList<>();

}
