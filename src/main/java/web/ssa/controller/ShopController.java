package web.ssa.controller;

// 올바른 부분 ✅
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.ssa.entity.Product;
import web.ssa.service.ProductService;

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

        List<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        model.addAttribute("loginUser", loginUser);
        return "productList"; // /WEB-INF/views/shop/productList.jsp
    }

    // 상품 상세 페이지 (로그인 필요)
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable Long id, HttpSession session, Model model) {
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetail"; // /WEB-INF/views/shop/productDetail.jsp
    }
}