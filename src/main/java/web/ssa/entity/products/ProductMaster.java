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

    @Column(name = "CATEGORY_ID", nullable = false)
    private int categoryId;

    @Column(name = "CATEGORY_CHILD_ID", nullable = false)
    private int categoryChildId;

    @Column(name = "SIMPLE_IMG")
    private int simpleImg;

    @Column(name = "DETAIL_IMG")
    private int detailImg;

    @Column
    private int price;

    @Column(columnDefinition = "json")
    private String detail;

    @Column(name = "DEFAULT_VARIANT") // 컬럼 생성됨
    private Integer defaultVariantId;

    @Column
    private int amount;

    @Column(name = "visit_count")
    private int count;

    @Column(nullable = false)
    private Date reg;

    @OneToMany(mappedBy = "masterId", cascade = CascadeType.ALL)
    List<ProductVariant> variants;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    List<ProductReview> reviews;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL)
    List<ReviewRecommend> commends;
}
