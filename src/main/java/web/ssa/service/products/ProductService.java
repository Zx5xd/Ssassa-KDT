package web.ssa.service.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;

import java.util.List;

@Service
public interface ProductService {
    List<ProductMaster> findByName(String name);
    List<ProductMaster> findById(int id);
    int delete(int id);

    Page<ProductMaster> findByCategoryId(int categoryId, Pageable pageable);
    Page<ProductMaster> findByCategoryChildId(int categoryChildId, Pageable pageable);

    Page<SimpleProductDTO> findBySimpleCategoryId(int categoryId, Pageable pageable);

    ProductImg findByImgId(int productImgId);
}
