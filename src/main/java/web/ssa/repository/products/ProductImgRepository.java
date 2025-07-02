package web.ssa.repository.products;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
   ProductImg findById(int id);
}
