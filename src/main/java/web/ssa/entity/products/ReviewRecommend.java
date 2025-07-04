package web.ssa.entity.products;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import web.ssa.entity.member.User;

import java.time.LocalDateTime;

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
    @JsonManagedReference
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID", nullable = false)
    private ProductReview reviewId;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "USER_IMGS", columnDefinition = "json")
    private String userImgs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @JsonIgnore
    private ProductMaster productId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VARIANT_ID", nullable = false)
    @JsonIgnore
    private ProductVariant productVariant;

    @CreationTimestamp
    @Column(name = "CREATED", updatable = false)
    private LocalDateTime created;

    @Column(name = "RECOMMEND_COUNT", nullable = false)
    private Integer recommendCount;

}