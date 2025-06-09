package web.ssa.service.products;

import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductReview;

import java.util.List;

@Service
public interface ProductReviewServ {
    List<ProductReview> getProductReviews();
    ProductReview getProductReview(int id);
    boolean saveProductReview(ProductReview productReview);
    boolean deleteProductReview(int id);
    boolean updateProductReview(ProductReview productReview);
}
