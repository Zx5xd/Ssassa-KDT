package web.ssa.service.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;
import web.ssa.entity.products.ProductVariant;
import web.ssa.entity.member.User;

import java.util.List;

@Service
public interface ProductReviewService {
    List<ProductReview> getProductReviews();
    ProductReview getProductReview(ProductMaster productMaster);
    ProductReview getProductReviewById(int id);
    boolean saveProductReview(ProductReview productReview);
    boolean deleteProductReview(int id);
    boolean updateProductReview(ProductReview productReview);

    Page<ProductReview> getPageReviews(int pid, int pvid, Pageable pageable);
    
    // ReviewRecommend 관련 메서드
    boolean saveReviewRecommend(ReviewRecommend reviewRecommend);
    
    // 기존 답글 조회 메서드
    ReviewRecommend getReviewRecommendByWriterAndReviewId(User writer, ProductReview reviewId);
    
    // 중복 체크 메서드
    boolean existsByWriterAndProductIdAndProductVariant(User writer, ProductMaster productId, ProductVariant productVariant);
    
    // reviewType을 포함한 중복 체크 메서드
    boolean existsByWriterAndProductIdAndProductVariantAndReviewType(User writer, ProductMaster productId, ProductVariant productVariant, int reviewType);
}
