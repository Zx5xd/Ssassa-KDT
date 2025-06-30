// ProductService.java
package web.ssa.service.products;

import web.ssa.entity.products.ProductMaster;

import java.util.List;

public interface ProductService {
    List<ProductMaster> getAllProducts(); // 엔티티 기준 통일

    ProductMaster getProductById(int id);

    List<ProductMaster> findByName(String name);

    List<ProductMaster> findById(int id);

    void delete(int id);

    // 상품 가격 조회 (variant 포함)
    int getProductPrice(int productId);

    // 상품 이미지 URL 조회 (캐시 사용)
    String getProductSimpleImg(ProductMaster product);

    // 상품 페이지네이션 (전체)
    org.springframework.data.domain.Page<ProductMaster> getPagedProducts(int page, int size);

    // 카테고리별 상품 페이지네이션
    org.springframework.data.domain.Page<ProductMaster> getPagedProductsByCategory(int categoryId, int page, int size);
}