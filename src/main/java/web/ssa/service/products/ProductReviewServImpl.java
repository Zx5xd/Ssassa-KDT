package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.repository.products.ProductReviewRepository;

import java.util.List;

@Service
public class ProductReviewServImpl implements ProductReviewServ {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Override
    public List<ProductReview> getProductReviews() {
        return this.productReviewRepository.findAll();
    }

    @Override
    public ProductReview getProductReview(ProductMaster productMaster) {
        return this.productReviewRepository.findProductReviewByProductId(productMaster);
    }

    @Override
    public boolean saveProductReview(ProductReview productReview) {
        ProductReview saved = this.productReviewRepository.save(productReview);
        boolean exists = this.productReviewRepository.existsById(saved.getId());;
        return exists;
    }

    @Override
    public boolean deleteProductReview(int id) {
        this.productReviewRepository.deleteById(id);
        return this.productReviewRepository.existsById(id);
    }

    @Override
    public boolean updateProductReview(ProductReview productReview) {
        ProductReview saved = this.productReviewRepository.save(productReview);
        boolean exists = this.productReviewRepository.existsById(saved.getId());;
        return exists;
    }

    public Page<ProductReview> getPagedReviews(int productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productReviewRepository.findByProductId(productId, pageable);
    }
}
