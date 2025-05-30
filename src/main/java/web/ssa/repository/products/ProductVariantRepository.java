package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductVariant;

import java.util.List;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findByMasterId(ProductMaster masterId);
    int deleteById(int id);
    int deleteByMasterId(ProductMaster productId);
}
