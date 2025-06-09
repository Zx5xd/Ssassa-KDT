package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductMaster, Integer> {
    List<ProductMaster> findByName(String name);
    List<ProductMaster> findById(int id);

    int deleteById(int id);

    // 관리자 관련
    int deleteByProductId(Integer id);
    int updateByProductMaster(ProductMaster productMaster);
    int createByProductMaster(ProductMaster productMaster);

}
