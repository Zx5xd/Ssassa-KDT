package web.ssa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.ssa.dto.categories.PLCategoryDTO;
import web.ssa.dto.products.IntegratedProductDTO;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ProductVariant;
import web.ssa.service.categories.CategoryChildServImpl;
import web.ssa.service.categories.CategoryServiceImpl;
import web.ssa.service.products.ProductReviewServiceImpl;
import web.ssa.service.products.ProductServiceImpl;
import web.ssa.service.products.ProductVariantServiceImpl;

import java.util.Map;

@Controller
@RequestMapping("/pd/*")
public class ProductController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private CategoryChildServImpl categoryChildServ;

    @Autowired
    private ProductServiceImpl pdServImpl;
    @Autowired
    private ProductVariantServiceImpl pdVariantServImpl;

    @Autowired
    private ProductReviewServiceImpl pdReviewServImpl;

    @GetMapping
    public Page<ProductMaster> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return pdServImpl.getPagedProducts(page, size);
    }

    @GetMapping("/{productId}/reviews")
    public Page<ProductReview> listReviews(
            @PathVariable int productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return pdReviewServImpl.getPagedReviews(productId, page, size);
    }

    @GetMapping("/")
    public Page<ProductMaster> listProducts(int categoryId, Pageable pageable) {
        return this.pdServImpl.findByCategoryId(categoryId, pageable);
    }

    @GetMapping("list")
    public String listProducts(@RequestParam(value = "cid", defaultValue = "-1") int cid,
                               @PageableDefault(page = 0, size = 30, sort = "id", direction = Sort.Direction.DESC)  Pageable pageable,
                               Model model) {

        Map<Integer, PLCategoryDTO> categoryMap = categoryService.getCategoryMap();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String categoryJson = mapper.writeValueAsString(categoryMap);

            model.addAttribute("categoryJson", categoryJson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Page<SimpleProductDTO> page = this.pdServImpl.findBySimpleCategoryId(cid,pageable);
        model.addAttribute("page",page);
        model.addAttribute("products", page.getContent());
        return "product/list";
    }

    @GetMapping("detail/{id}")
    public String detail(@PathVariable String id, Model model) {
        try {
            if (id.contains("_")) {
                // 예: 11_5
                String[] parts = id.split("_");
                int pid = Integer.parseInt(parts[0]);
                int pvid = Integer.parseInt(parts[1]);

                // 🔍 ProductVariant만 조회
                String name = this.pdServImpl.findNameById(pid);
                ProductVariant variant = this.pdVariantServImpl.getVariantById(pvid);
                if (variant == null) {
                    return "redirect:/error";
                }
                variant.setName(name + "-" + variant.getName());

                IntegratedProductDTO dto = IntegratedProductDTO.from(variant);

                model.addAttribute("product", dto);
                return "product/detail";

            } else {
                // 예: 11
                int pid = Integer.parseInt(id);

                // 🔍 ProductMaster만 조회
                ProductMaster product = this.pdServImpl.findById(pid);
                if (product == null) {
                    return "redirect:/error";
                }

                IntegratedProductDTO dto = IntegratedProductDTO.from(product);

                model.addAttribute("product", dto);
                return "product/detail";
            }

        } catch (Exception e) {
            return "redirect:/error";
        }
    }



    // ManageMent

}