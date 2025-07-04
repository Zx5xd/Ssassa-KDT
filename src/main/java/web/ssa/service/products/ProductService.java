// ProductService.java
package web.ssa.service.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.entity.products.ProductMaster;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductMaster> getAllProducts(); // 엔티티 기준 통일

    ProductMaster getProductById(int id);

    List<ProductMaster> findByName(String name);

    ProductMaster findById(int id);
    String findNameById(int id);
    int delete(int id);

    Page<ProductMaster> findByCategoryId(int categoryId, Pageable pageable);
    Page<ProductMaster> findByCategoryChildId(int categoryChildId, Pageable pageable);

    Page<SimpleProductDTO> findBySimpleCategoryId(int categoryId, Pageable pageable);

    ProductImg findByImgId(int productImgId);

    // 논리적 삭제: amount를 -1로 설정
    void softDeleteProduct(int productId);

    // 상품 저장
    void saveProduct(ProductCreateDTO product);

    // 상품 저장 (변형 포함)
    void saveProductWithVariants(ProductCreateDTO product);

    // 상품 가격 조회 (variant 포함)
    int getProductPrice(int productId);

    // 상품 이미지 URL 조회 (캐시 사용)
    String getProductSimpleImg(ProductMaster product);

    // 상품 페이지네이션 (전체)
    Page<ProductMaster> getPagedProducts(int page, int size);

    // 카테고리별 상품 페이지네이션
    Page<ProductMaster> getPagedProductsByCategory(int categoryId, int page, int size);

    // 상품명으로 검색
    Page<ProductMaster> searchProducts(String keyword, int page, int size);

    void saveProductImg(String img, int imgId);

    void updateProduct(int id, ProductCreateDTO editProduct, ProductDTO originalProduct);

    int uploadImg(String img);

    // 상품 수정 (변형 포함)
    void updateProductWithVariants(int id, ProductCreateDTO editProduct, ProductDTO originalProduct);

    // 동적 필터 검색
    List<ProductMaster> searchByDynamicFilter(Map<String, List<String>> filterMap);
}