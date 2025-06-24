package web.ssa.repository.products;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
    ProductReview findProductReviewByProductId(ProductMaster productId);
    int countProductReviewByProductId(ProductMaster productId);

    void deleteById(int id);

    Page<ProductReview> findByProductId(int productId, Pageable pageable);

}
