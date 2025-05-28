package web.ssa.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.products.ProductImg;

public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
    ProductImg findById(int id);
    int deleteById(int id);
}
