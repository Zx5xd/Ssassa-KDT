package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    List<ProductReview> findByProductId(ProductMaster productId);
    int countProductReviewByProductId(ProductMaster productId);
    int deleteById(int id);
}
