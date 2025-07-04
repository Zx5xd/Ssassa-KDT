package web.ssa.entity.products;

import jakarta.persistence.*;
import lombok.*;
import web.ssa.entity.member.User;

@Entity
@Table(name = "REVIEW_RECOMMEND")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="writer", nullable = false)
    private User writer;

    @ManyToOne
    @JoinColumn(name = "REVIEW_ID", nullable = false)
    private ProductReview reviewId;

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
