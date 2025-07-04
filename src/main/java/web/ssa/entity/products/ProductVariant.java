package web.ssa.entity.products;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT_VARIANT")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "MASTER_ID", nullable = false)
    private ProductMaster masterId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String name;

    @Column(name="SIMPLE_IMG")
    private int simpleImg;

    @Column(name = "DETAIL_IMG")
    private int detailImg;

    @Column(columnDefinition = "json", nullable = false)
    private String detail;

    @Column
    private int amount;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductReview> reviews = new ArrayList<>();
}
