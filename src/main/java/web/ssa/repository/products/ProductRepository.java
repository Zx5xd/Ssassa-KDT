package web.ssa.repository.products;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductMaster;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductMaster, Integer> {
    List<ProductMaster> findByName(String name);
    List<ProductMaster> findById(int id);

    int deleteById(int id);
    @Modifying
    @Transactional
    @Query("UPDATE ProductMaster p SET p.name = :name WHERE p.id = :id")
    int updateProductNameById(@Param("id") int id, @Param("name") String name);

    Page<ProductMaster> findAll(Pageable pageable);

    // 상품 등록 <- 기본
    //

}
