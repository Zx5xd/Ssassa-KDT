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

    Page<ProductMaster> findByCategoryId(int categoryId, Pageable pageable);

    // amount가 -1이 아닌 상품만 조회 (삭제되지 않은 상품)
    Page<ProductMaster> findByAmountNot(int amount, Pageable pageable);

    // 카테고리별로 amount가 -1이 아닌 상품만 조회
    Page<ProductMaster> findByCategoryIdAndAmountNot(int categoryId, int amount, Pageable pageable);

    // 상품명으로 검색 (amount가 -1이 아닌 상품만)
    Page<ProductMaster> findByNameContainingAndAmountNot(String name, int amount, Pageable pageable);
}
