package web.ssa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ssa.dto.PageResponse;
import web.ssa.dto.products.ProductReviewFormDTO;
import web.ssa.entity.member.User;
import web.ssa.entity.products.ProductReview;
import web.ssa.service.WebDAVService;
import web.ssa.service.products.ProductReviewServiceImpl;
import web.ssa.service.products.ProductServiceImpl;
import web.ssa.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review/*")
public class ReviewRestController {
    @Autowired
    private ProductReviewServiceImpl productReviewService;
    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("list")
    public ResponseEntity<Page<ProductReview>> getPageReview(
            @RequestParam(value = "pid") int pid,
            @RequestParam(value = "pvid", defaultValue = "-1") int  pvid,
            Pageable pageable
    ) {
        Page<ProductReview> reviews = this.productReviewService.getPageReviews(pid, pvid, pageable);
        if (reviews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("submit")
    public ResponseEntity<ProductReview> uploadReview(@ModelAttribute ProductReviewFormDTO dto, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Integer> imgId = new ArrayList<>();
        WebDAVService webDAVService = new WebDAVService();
        ObjectMapper mapper = new ObjectMapper();

        try {
            for (MultipartFile img : dto.getImages()) {
                String filename = img.getName();
                byte[] webpBytes = ImageUtil.convertToWebP(img.getInputStream());
                MultipartFile webpFile = ImageUtil.toMultipartFile(webpBytes, filename);
                String name = webDAVService.uploadFile(webpFile);
                imgId.add(this.productService.uploadImg(name));
            }

            String json = mapper.writeValueAsString(imgId);
            ProductReview productReview = ProductReview.builder()
//                    .writer(user.getNickname())
                    .writer(user)
                    .content(dto.getContent())
                    .userImgs(json)
                    .build();
            this.productReviewService.saveProductReview(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return null;
    }

    @DeleteMapping("delete")
    public ResponseEntity<Page<ProductReview>> deleteReview(@RequestParam("id") int id) {
        return null;
    }
}
