package web.ssa.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import web.ssa.util.*;
import jakarta.servlet.http.HttpSession;
import web.ssa.cache.CategoriesCache;
import web.ssa.cache.ProductImgCache;
import web.ssa.dto.products.ProductCreateDTO;
import web.ssa.dto.products.ProductDTO;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.mapper.ConvertToDTO;
import web.ssa.service.categories.CategoryService;
import web.ssa.service.products.ProductReviewServImpl;
import web.ssa.service.products.ProductService;
import web.ssa.service.products.ProductServiceImpl;
import web.ssa.service.products.ProductVariantService;
import web.ssa.util.FormatUtil;
import web.ssa.service.WebDAVService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.io.IOException;

@Controller
@RequestMapping("/pd")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductServiceImpl pdServImpl;
    private final ProductReviewServImpl pdReviewServImpl;
    private final ProductVariantService productVariantService;
    private final CategoriesCache categoriesCache;
    private final ProductImgCache productImgCache;
    private final AdminController adminController;
    private final WebDAVService webDAVService;

    // 상품 목록 페이지 (카테고리별 필터링 지원)
    @GetMapping("/get/products")
    public String listProducts(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model,
            HttpSession session) {
        int pageSize = 20;

        Page<ProductMaster> productPage;

        if (search != null && !search.trim().isEmpty()) {
            // 검색어가 있는 경우
            productPage = productService.searchProducts(search.trim(), page - 1, pageSize);
        } else if (categoryId != null) {
            // 카테고리 필터링
            productPage = productService.getPagedProductsByCategory(categoryId, page - 1, pageSize);
        } else {
            // 전체 상품
            productPage = productService.getPagedProducts(page - 1, pageSize);
        }

        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("productPage", productPage);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("searchKeyword", search);
        model.addAttribute("formatUtil", new FormatUtil());
        model.addAttribute("productVariantService", productVariantService);

        if (adminController.isAdmin(session)) {
            return "admin/productList";
        } else {
            return "shop/productList";
        }
    }

    // 상품 삭제 (amount를 -1로 설정)
    @PostMapping("/set/product/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") int id,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "page", required = false) Integer page,
            HttpSession session) {
        // amount를 -1로 설정하여 논리적 삭제
        productService.softDeleteProduct(id);

        String redirectUrl = "pd/get/products";
        if (categoryId != null) {
            redirectUrl += "?categoryId=" + categoryId;
        }
        if (page != null) {
            redirectUrl += (categoryId != null ? "&" : "?") + "page=" + page;
        }

        if (adminController.isAdmin(session)) {
            return "redirect:" + redirectUrl;
        } else {
            return "redirect:/login";
        }
    }

    // 상품 등록 페이지
    @GetMapping("/set/product/new")
    public String newProductForm(Model model, HttpSession session) {
        model.addAttribute("category", categoriesCache.getCachedCategories());
        if (adminController.isAdmin(session)) {
            return "admin/productForm";
        } else {
            return "redirect:/login";
        }
    }

    // 상품 등록 처리
    @PostMapping("/set/product/create")
    public String createProduct(@ModelAttribute ProductCreateDTO cp,
            @RequestParam(value = "simpleImg", required = false) MultipartFile simpleImg,
            @RequestParam(value = "detailImg", required = false) MultipartFile detailImg,
            HttpSession session) {
        if (!adminController.isAdmin(session)) {
            return "redirect:/login";
        }

        cp.setCategoryId(Integer.parseInt(cp.getStrCategoryId()));

        if (cp.getStrCategoryChildId().isEmpty()) {
            cp.setCategoryChildId(0);
        } else {
            cp.setCategoryChildId(Integer.parseInt(cp.getStrCategoryChildId()));
        }

        // WebDAV를 통한 파일 업로드
        try {
            if (simpleImg != null && !simpleImg.isEmpty()) {
                String simpleImgUrl = webDAVService.uploadFile(simpleImg);
                // URL에서 파일명만 추출하여 저장
                String simpleImgFileName = simpleImgUrl.substring(simpleImgUrl.lastIndexOf("/") + 1);
                cp.setSimpleImgFileName(simpleImgFileName);
            }

            if (detailImg != null && !detailImg.isEmpty()) {
                String detailImgUrl = webDAVService.uploadFile(detailImg);
                // URL에서 파일명만 추출하여 저장
                String detailImgFileName = detailImgUrl.substring(detailImgUrl.lastIndexOf("/") + 1);
                cp.setDetailImgFileName(detailImgFileName);
            }

            // 상품 저장
            productService.saveProduct(cp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/pd/get/products";
    }

    // 상품 수정 페이지
    @GetMapping("/set/product/edit/{id}")
    public String editProductForm(@PathVariable("id") int id, Model model, HttpSession session) {
        if (!adminController.isAdmin(session)) {
            return "redirect:/login";
        }

        ProductDTO product = ConvertToDTO.productToDTO(this.productService.getProductById(id));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("regStr", product.getReg() != null ? sdf.format(product.getReg()) : "");
        model.addAttribute("product", product);
        model.addAttribute("category", categoriesCache.getCachedCategories());
        model.addAttribute("productImgCache", this.productImgCache);

        return "admin/productEditForm";
    }

    @PostMapping("/set/product/update/{id}")
    public String updateProduct(@PathVariable("id") int id, @ModelAttribute ProductCreateDTO cp, HttpSession session) {
        if (!adminController.isAdmin(session)) {
            return "redirect:/login";
        }

        ProductDTO originalProduct = ConvertToDTO.productToDTO(this.productService.getProductById(id));

        cp.setCategoryId(Integer.parseInt(cp.getStrCategoryId()));

        if (cp.getStrCategoryChildId().isEmpty()) {
            cp.setCategoryChildId(0);
        } else {
            cp.setCategoryChildId(Integer.parseInt(cp.getStrCategoryChildId()));
        }

        this.productService.updateProduct(id, cp, originalProduct);
        return "redirect:/pd/get/products";
    }

    // 상품 리뷰 목록 (API)
    @GetMapping("/{productId}/reviews")
    @ResponseBody
    public Page<ProductReview> listReviews(
            @PathVariable("productId") int productId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return pdReviewServImpl.getPagedReviews(productId, page, size);
    }

    // ManageMent

}