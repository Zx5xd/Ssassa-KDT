// ProductService.java
package web.ssa.service;

import web.ssa.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();               // ✅ 엔티티 기준 통일
    Product getProductById(Long id);
}