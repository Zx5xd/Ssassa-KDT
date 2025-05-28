package web.ssa.service.products;

import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductMaster;

import java.util.List;

@Service
public interface ProductService {
    List<ProductMaster> findByName(String name);
    List<ProductMaster> findById(int id);
    int delete(int id);
}
