package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    // productId(ProductMaster의 id)로 해당 상품의 모든 variant 조회
    List<ProductVariant> findByMasterId_Id(int masterId);

    // variantId로 단일 variant 조회는 JpaRepository의 findById 사용
}
