package web.ssa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.ssa.dto.PageResponse;
import web.ssa.dto.products.CommentTypeDTO;
import web.ssa.dto.products.ProductReviewFormDTO;
import web.ssa.entity.member.User;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ProductVariant;
import web.ssa.entity.products.ReviewRecommend;
import web.ssa.service.WebDAVService;
import web.ssa.service.products.ProductReviewServiceImpl;
import web.ssa.service.products.ProductServiceImpl;
import web.ssa.service.products.ProductVariantService;
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
    @Autowired
    private ProductVariantService productVariantService;

    @GetMapping("list")
    public ResponseEntity<Page<CommentTypeDTO>> getPageReview(
            @RequestParam(value = "pid") int pid,
            @RequestParam(value = "pvid", defaultValue = "-1") int  pvid,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductReview> reviews = this.productReviewService.getPageReviews(pid, pvid, pageable);
        if (reviews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        // ProductReview를 CommentTypeDTO로 변환
        Page<CommentTypeDTO> commentDTOs = reviews.map(CommentTypeDTO::fromProductReview);
        
        return ResponseEntity.ok(commentDTOs);
    }

    @PostMapping("submit")
    public ResponseEntity<Object> uploadReview(@ModelAttribute ProductReviewFormDTO dto, HttpSession session) {
        System.out.println("=== Review Submit Debug Start ===");
        System.out.println("Received DTO: " + dto);
        System.out.println("DTO Type: " + dto.getType());
        System.out.println("DTO Content: " + dto.getContent());
        System.out.println("DTO PID: " + dto.getPid());
        System.out.println("DTO PVID: " + dto.getPvid());
        System.out.println("DTO ParentReviewId: " + dto.getParentReviewId());
        System.out.println("DTO Images: " + (dto.getImages() != null ? dto.getImages().size() : "null"));
        
        User user = (User) session.getAttribute("loginUser");
        System.out.println("Session User: " + (user != null ? user.getEmail() : "null"));
        if (user == null) {
            System.out.println("ERROR: User not found in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // ProductMaster와 ProductVariant 조회
        System.out.println("Looking up ProductMaster with ID: " + dto.getPid());
        ProductMaster productMaster = this.productService.getProductById(dto.getPid());
        System.out.println("ProductMaster found: " + (productMaster != null ? "YES" : "NO"));
        if (productMaster == null) {
            System.out.println("ERROR: ProductMaster not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProductVariant productVariant;
        if (dto.getPvid() > 0) {
            System.out.println("Looking up ProductVariant with ID: " + dto.getPvid());
            productVariant = this.productVariantService.getVariantById(dto.getPvid());
            System.out.println("ProductVariant found: " + (productVariant != null ? "YES" : "NO"));
            if (productVariant == null) {
                System.out.println("ERROR: ProductVariant not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            // 기본 variant 사용
            System.out.println("Using default variant, ProductMaster defaultVariantId: " + productMaster.getDefaultVariantId());
            productVariant = this.productVariantService.getVariantById(productMaster.getDefaultVariantId());
            System.out.println("Default ProductVariant found: " + (productVariant != null ? "YES" : "NO"));
            if (productVariant == null) {
                System.out.println("ERROR: Default ProductVariant not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

        List<Integer> imgId = new ArrayList<>();
        WebDAVService webDAVService = new WebDAVService();
        ObjectMapper mapper = new ObjectMapper();

        try {
            System.out.println("Processing images...");
            // 이미지 처리
            if (dto.getImages() != null && !dto.getImages().isEmpty()) {
                System.out.println("Number of images to process: " + dto.getImages().size());
                for (int i = 0; i < dto.getImages().size(); i++) {
                    MultipartFile img = dto.getImages().get(i);
                    System.out.println("Processing image " + (i + 1) + ": " + (img != null ? img.getOriginalFilename() : "null"));
                    if (img != null && !img.isEmpty()) {
                        String filename = img.getOriginalFilename();
                        System.out.println("Converting image to WebP: " + filename);
                        byte[] imageBytes = ImageUtil.convertToWebP(img.getInputStream());
                        MultipartFile convertedFile = ImageUtil.toMultipartFile(imageBytes, filename);
                        System.out.println("Uploading to WebDAV: " + convertedFile.getOriginalFilename());
                        System.out.println(convertedFile.getOriginalFilename());
                        String name = webDAVService.uploadFile(convertedFile);
                        System.out.println("WebDAV upload result: " + name);
                        int uploadedImgId = this.productService.uploadImg(name);
                        System.out.println("Image uploaded with ID: " + uploadedImgId);
                        imgId.add(uploadedImgId);
                    } else {
                        System.out.println("Skipping null or empty image at index " + i);
                    }
                }
            } else {
                System.out.println("No images to process");
            }

            String json = mapper.writeValueAsString(imgId);
            System.out.println("Image IDs JSON: " + json);
            
            // 답변인 경우 ReviewRecommend에 저장, 그 외에는 ProductReview에 저장
            System.out.println("Checking if this is an answer: " + dto.isAnswer());
            if (dto.isAnswer()) {
                System.out.println("Processing as ANSWER");
                // 답변을 저장할 리뷰 ID가 필요함 - DTO에 추가 필요
                if (dto.getParentReviewId() <= 0) {
                    System.out.println("ERROR: ParentReviewId is required for answers but was: " + dto.getParentReviewId());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
                
                // 부모 리뷰 조회
                System.out.println("Looking up parent review with ID: " + dto.getParentReviewId());
                ProductReview parentReview = this.productReviewService.getProductReviewById(dto.getParentReviewId());
                System.out.println("Parent review found: " + (parentReview != null ? "YES" : "NO"));
                if (parentReview == null) {
                    System.out.println("ERROR: Parent review not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                
                System.out.println("Building ReviewRecommend...");
                ReviewRecommend reviewRecommend = ReviewRecommend.builder()
                        .writer(user)
                        .reviewId(parentReview) // 부모 리뷰 설정
                        .content(dto.getContent())
                        .userImgs(json)
                        .productId(productMaster)
                        .productVariant(productVariant)
                        .recommendCount(0)
                        .build();
                
                System.out.println("Saving ReviewRecommend...");
                boolean saved = this.productReviewService.saveReviewRecommend(reviewRecommend);
                System.out.println("ReviewRecommend save result: " + saved);
                if (saved) {
                    System.out.println("SUCCESS: ReviewRecommend saved successfully");
                    CommentTypeDTO commentDTO = CommentTypeDTO.fromReviewRecommend(reviewRecommend);
                    return ResponseEntity.ok().body(commentDTO);
                } else {
                    System.out.println("ERROR: Failed to save ReviewRecommend");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                System.out.println("Processing as REVIEW/QUESTION");
                // 리뷰 또는 질문인 경우 ProductReview에 저장
                System.out.println("ReviewType: " + dto.getReviewType());
                System.out.println("Building ProductReview...");
                System.out.println("Writer ID: " + user.getEmail());
                System.out.println("ProductMaster ID: " + productMaster.getId());
                System.out.println("ProductVariant ID: " + productVariant.getId());
                System.out.println("Content: " + dto.getContent());
                System.out.println("ReviewType: " + dto.getReviewType());
                
                ProductReview productReview = ProductReview.builder()
                        .writer(user)
                        .content(dto.getContent())
                        .userImgs(json)
                        .productId(productMaster)
                        .productVariant(productVariant)
                        .recommendCount(0)
                        .reviewType(dto.getReviewType()) // DTO의 변환 메서드 사용
                        .build();
                
                System.out.println("ProductReview object created successfully");
                System.out.println("Saving ProductReview...");
                boolean saved = this.productReviewService.saveProductReview(productReview);
                System.out.println("ProductReview save result: " + saved);
                
                if (saved) {
                    System.out.println("SUCCESS: ProductReview saved successfully");
                    CommentTypeDTO commentDTO = CommentTypeDTO.fromProductReview(productReview);
                    return ResponseEntity.ok().body(commentDTO);
                } else {
                    System.out.println("ERROR: Failed to save ProductReview");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        } catch (Exception e) {
            System.out.println("EXCEPTION occurred during processing:");
            e.printStackTrace();
            System.out.println("Exception message: " + e.getMessage());
            System.out.println("Exception type: " + e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } finally {
            System.out.println("=== Review Submit Debug End ===");
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteReview(@RequestParam("id") int id, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // 리뷰 존재 여부 확인을 위해 ProductMaster로 조회
            ProductMaster productMaster = this.productService.getProductById(1); // 임시로 1 사용, 실제로는 리뷰 ID로 조회해야 함
            ProductReview review = this.productReviewService.getProductReview(productMaster);
            if (review == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // 작성자만 삭제 가능 (임시로 주석 처리)
            // if (!review.getWriter().getId().equals(user.getId())) {
            //     return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            // }

            boolean deleted = this.productReviewService.deleteProductReview(id);
            if (deleted) {
                return ResponseEntity.ok("Review deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
