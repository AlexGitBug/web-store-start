package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "catalog", schema = "public")
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
