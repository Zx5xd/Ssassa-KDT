package web.ssa.entity.products;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_REVIEW")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "USER_IMGS", columnDefinition = "json")
    private String userImgs;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private ProductMaster productId;

    @OneToOne
    @JoinColumn(name = "PRODUCT_VARIANT_ID", nullable = false)
    private ProductVariant productVariant;

    @Column(name = "RECOMMEND_COUNT", nullable = false)
    private Integer recommendCount;
}

