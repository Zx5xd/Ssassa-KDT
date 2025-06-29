package web.ssa.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.service.products.ProductReviewServImpl;
import web.ssa.service.products.ProductServiceImpl;

@RestController
@RequestMapping("/pd/*")
public class ProductController {

    @Autowired
    private ProductServiceImpl pdServImpl;

    @Autowired
    private ProductReviewServImpl pdReviewServImpl;

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


    // ManageMent

}