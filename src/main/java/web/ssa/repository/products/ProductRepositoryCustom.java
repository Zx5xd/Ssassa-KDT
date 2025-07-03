package web.ssa.repository.products;

import web.ssa.entity.products.ProductMaster;
import java.util.List;
import java.util.Map;

public interface ProductRepositoryCustom {
    List<ProductMaster> searchByDynamicFilter(Map<String, List<String>> filterMap);
}