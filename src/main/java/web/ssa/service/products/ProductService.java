// ProductService.java
package web.ssa.service.products;

import web.ssa.entity.products.ProductMaster;

import java.util.List;

public interface ProductService {
    List<ProductMaster> getAllProducts(); // 엔티티 기준 통일

    ProductMaster getProductById(Long id);

    List<ProductMaster> findByName(String name);

    List<ProductMaster> findById(int id);

    void delete(int id);

    // 상품 가격 조회 (variant 포함)
    int getProductPrice(Long productId);

    // 상품 이미지 URL 조회 (캐시 사용)
    String getProductSimpleImg(ProductMaster product);
}