package web.ssa.controller.admin;

// 올바른 부분 ✅
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.ssa.entity.products.ProductMaster;
import web.ssa.service.products.ProductService;

import java.util.List;

@Controller
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ProductService productService;

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
        return "productList"; // /WEB-INF/views/shop/productList.jsp
    }

    // 상품 상세 페이지 (로그인 필요)
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable Long id, HttpSession session, Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        ProductMaster product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetail"; // /WEB-INF/views/shop/productDetail.jsp
    }
}