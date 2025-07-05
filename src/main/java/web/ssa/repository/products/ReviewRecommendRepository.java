package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.member.User;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;

@Repository
public interface ReviewRecommendRepository extends JpaRepository<ReviewRecommend, Integer> {
    ReviewRecommend findByWriterAndReviewId(User writer, ProductReview reviewId);
}