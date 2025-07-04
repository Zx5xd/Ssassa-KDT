package web.ssa.entity.products;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import web.ssa.entity.member.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PRODUCT_REVIEW")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "writer")
    @JsonManagedReference
    private User writer;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(name = "USER_IMGS", columnDefinition = "json")
    private String userImgs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    @JsonIgnore
    private ProductMaster productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_VARIANT_ID", nullable = true)
    @JsonIgnore
    private ProductVariant productVariant;

    @Column(name = "RECOMMEND_COUNT", nullable = false)
    private Integer recommendCount;

    @Column(name = "REVIEW_TYPE", nullable = false)
    private int reviewType;

    @CreationTimestamp
    @Column(name = "CREATED", updatable = false)
    private LocalDateTime created;

    @OneToMany(mappedBy = "reviewId", cascade = CascadeType.ALL)
    List<ReviewRecommend> recommendList;
}