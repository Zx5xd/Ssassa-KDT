package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductMaster;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductMaster, Integer> {
    List<ProductMaster> findByProductName(String name);
    List<ProductMaster> findByProductId(Integer id);
}
