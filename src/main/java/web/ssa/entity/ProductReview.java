package web.ssa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_REVIEW")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductReview {
    @Id
    private int id;

    @Column(nullable = false)
    private String writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "USER_IMGS", columnDefinition = "json")
    private String userImgs;

    @Column(name = "PRODUCT_ID", nullable = false)
    private int productId;

    @Column(name = "PRODUCT_VARIANT_ID", nullable = false)
    private int productVariantId;

    @Column(name = "RECOMMEND_COUNT", nullable = false)
    private Integer recommendCount;
}

