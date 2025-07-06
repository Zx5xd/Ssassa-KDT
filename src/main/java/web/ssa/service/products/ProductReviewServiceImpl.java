package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;
import web.ssa.entity.products.ProductVariant;
import web.ssa.entity.member.User;
import web.ssa.repository.products.ProductRepository;
import web.ssa.repository.products.ProductReviewRepository;
import web.ssa.repository.products.ReviewRecommendRepository;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductReviewRepository productReviewRepository;
    @Autowired
    private ReviewRecommendRepository reviewRecommendRepository;

    @Override
    public List<ProductReview> getProductReviews() {
        return this.productReviewRepository.findAll();
    }

    @Override
    public ProductReview getProductReview(ProductMaster productMaster) {
        return this.productReviewRepository.findProductReviewByProductId(productMaster);
    }

    @Override
    public ProductReview getProductReviewById(int id) {
        return this.productReviewRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProductReview> getPageReviews(int pid, int pvid, Pageable pageRequest) {
        ProductMaster productMaster = this.productRepository.findById(pid);
        if (pvid != -1) {
            return this.productReviewRepository.findByProductIdAndProductVariantId(productMaster, pvid, pageRequest);
        }
        return this.productReviewRepository.findByProductId(productMaster, pageRequest);
    }

    @Override
    public boolean saveProductReview(ProductReview productReview) {
        ProductReview saved = this.productReviewRepository.save(productReview);
        boolean exists = this.productReviewRepository.existsById(saved.getId());;
        return exists;
    }

    @Override
    public boolean deleteProductReview(int id) {
        try {
            // 삭제 전에 존재하는지 확인
            if (!this.productReviewRepository.existsById(id)) {
                System.out.println("🔍 삭제할 ProductReview가 존재하지 않음 - id: " + id);
                return false;
            }
            
            this.productReviewRepository.deleteById(id);
            
            // 삭제 후 존재하지 않는지 확인
            boolean stillExists = this.productReviewRepository.existsById(id);
            if (stillExists) {
                System.out.println("🔍 ProductReview 삭제 실패 - 여전히 존재함 - id: " + id);
                return false;
            } else {
                System.out.println("🔍 ProductReview 삭제 성공 - id: " + id);
                return true;
            }
        } catch (Exception e) {
            System.out.println("🔍 ProductReview 삭제 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProductReview(ProductReview productReview) {
        ProductReview saved = this.productReviewRepository.save(productReview);
        boolean exists = this.productReviewRepository.existsById(saved.getId());;
        return exists;
    }

    public Page<ProductReview> getPagedReviews(int productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        ProductMaster product = this.productRepository.findById(productId);
        return productReviewRepository.findByProductId(product, pageable);
    }

    @Override
    public boolean saveReviewRecommend(ReviewRecommend reviewRecommend) {
        try {
            ReviewRecommend saved = this.reviewRecommendRepository.save(reviewRecommend);
            return this.reviewRecommendRepository.existsById(saved.getId());
        } catch (Exception e) {
            System.out.println("Error saving ReviewRecommend: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ReviewRecommend getReviewRecommendByWriterAndReviewId(User writer, ProductReview reviewId) {
        return this.reviewRecommendRepository.findByWriterAndReviewId(writer, reviewId);
    }

    @Override
    public boolean existsByWriterAndProductIdAndProductVariant(User writer, ProductMaster productId, ProductVariant productVariant) {
        return this.productReviewRepository.existsByWriterAndProductIdAndProductVariant(writer, productId, productVariant);
    }

    @Override
    public boolean existsByWriterAndProductIdAndProductVariantAndReviewType(User writer, ProductMaster productId, ProductVariant productVariant, int reviewType) {
        return this.productReviewRepository.existsByWriterAndProductIdAndProductVariantAndReviewType(writer, productId, productVariant, reviewType);
    }

    @Override
    public ReviewRecommend getReviewRecommendById(int id) {
        return this.reviewRecommendRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateReviewRecommend(ReviewRecommend reviewRecommend) {
        try {
            ReviewRecommend saved = this.reviewRecommendRepository.save(reviewRecommend);
            return this.reviewRecommendRepository.existsById(saved.getId());
        } catch (Exception e) {
            System.out.println("Error updating ReviewRecommend: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteReviewRecommend(int id) {
        try {
            this.reviewRecommendRepository.deleteById(id);
            return !this.reviewRecommendRepository.existsById(id);
        } catch (Exception e) {
            System.out.println("Error deleting ReviewRecommend: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
