package web.ssa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import web.ssa.cache.CategoriesCache;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.service.categories.CategoryService;
import web.ssa.service.products.ProductReviewServImpl;
import web.ssa.service.products.ProductService;
import web.ssa.service.products.ProductServiceImpl;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductServiceImpl pdServImpl;
    private final ProductReviewServImpl pdReviewServImpl;
    private final CategoriesCache categoriesCache;

    @GetMapping("/admin/list")
    public String listProducts(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
        int pageSize = 20;

        Page<ProductMaster> productPage = (categoryId == null)
                ? productService.getPagedProducts(page - 1, pageSize)
                : productService.getPagedProductsByCategory(categoryId, page - 1, pageSize);

        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("productPage", productPage);
        model.addAttribute("selectedCategoryId", categoryId);
        return "admin/productList";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") int id,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "page", required = false) Integer page) {
        productService.delete(id);
        return "redirect:/admin/products?categoryId=" + (categoryId != null ? categoryId : "") + "&page="
                + (page != null ? page : 1);
    }

    @GetMapping("/admin/new")
    public String newProductForm(Model model) {
        // 상품 등록 폼으로 이동 (폼 구현은 별도)
        return "admin/productForm";
    }

    @GetMapping("/{productId}/reviews")
    public Page<ProductReview> listReviews(
            @PathVariable("productId") int productId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return pdReviewServImpl.getPagedReviews(productId, page, size);
    }

    // ManageMent

}