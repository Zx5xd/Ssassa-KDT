package web.ssa.entity.products;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PRODUCT_MASTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "CATEOGRY_ID", nullable = false)
    private int categoryId;

    @Column(name = "DETAIL_CATEGORY_ID", nullable = false)
    private int detailCategoryId;

    @Column(name = "SIMPLE_IMG")
    private int simpleImg;

    @Column(name = "DETAIL_IMG")
    private int detailImg;

    @Column
    private int price;

    @Column(columnDefinition = "json")
    private String detail;

    @OneToMany(mappedBy = "masterId", cascade = CascadeType.ALL)
    List<ProductVariant> defaultVariant;

    @Column(nullable = false)
    private Date reg;
}
