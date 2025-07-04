package web.ssa.controller.admin;

// 올바른 부분 
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.ssa.entity.products.ProductMaster;
import web.ssa.service.products.ProductService;
import web.ssa.cache.CategoriesCache;

import java.util.List;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;
    private final CategoriesCache categoriesCache;

    // 상품 전체 목록 (로그인 필요)
    @GetMapping("/products")
    public String showProductList(HttpSession session, Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        List<ProductMaster> productList = productService.getAllProducts();

        // 각 상품에 이미지 URL 추가
        productList.forEach(product -> {
            String imgUrl = productService.getProductSimpleImg(product);
            // ProductMaster에 임시로 이미지 URL 설정 (실제로는 DTO 사용 권장)
            // 여기서는 간단히 model에 별도로 추가
        });

        model.addAttribute("products", productList);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("productService", productService); // JSP에서 사용할 수 있도록
        model.addAttribute("categoriesCache", categoriesCache); // 카테고리 캐시 추가
        return "product/list"; // /WEB-INF/views/shop/productList.jsp
    }

    // 상품 상세 페이지 (로그인 필요)
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable("id") int id, HttpSession session, Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        ProductMaster product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/shop/products";
        }

        // 카테고리 이름 가져오기
        String categoryName = categoriesCache.getCachedCategories().stream()
                .filter(category -> category.getId() == product.getCategoryId())
                .findFirst()
                .map(category -> category.getName())
                .orElse("카테고리 정보 없음");

        model.addAttribute("product", product);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("categoriesCache", categoriesCache);
        return "product/detail"; // /WEB-INF/views/shop/productDetail.jsp
    }
}