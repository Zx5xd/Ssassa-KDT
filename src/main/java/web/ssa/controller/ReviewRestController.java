package web.ssa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/review/*")
public class ReviewRestController {
    @Autowired
    private ProductReviewServiceImpl productReviewService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private WebDAVService webDAVService;

    @GetMapping("list")
    public ResponseEntity<Page<CommentTypeDTO>> getPageReview(
            @RequestParam(value = "pid") int pid,
            @RequestParam(value = "pvid", defaultValue = "-1") int pvid,
            @RequestParam(value = "filter", defaultValue = "") String filter,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ProductReview> reviews = this.productReviewService.getPageReviews(pid, pvid, pageable);
        if (reviews == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        // ProductReview를 CommentTypeDTO로 변환
        Page<CommentTypeDTO> commentDTOs = reviews.map(CommentTypeDTO::fromProductReview);
        
        // 필터링 적용
        if (filter != null && !filter.isEmpty()) {
            List<CommentTypeDTO> filteredContent = commentDTOs.getContent().stream()
                .filter(comment -> {
                    switch (filter) {
                        case "review":
                            return "review".equals(comment.getType());
                        case "question":
                            return "question".equals(comment.getType());
                        default:
                            return true; // 전체인 경우 모든 댓글 포함
                    }
                })
                .collect(Collectors.toList());
            
            // 필터링된 결과로 새로운 Page 객체 생성
            Page<CommentTypeDTO> filteredPage = new PageImpl<>(
                filteredContent,
                pageable,
                filteredContent.size()
            );
            
            return ResponseEntity.ok(filteredPage);
        }
        
        return ResponseEntity.ok(commentDTOs);
    }

    @PostMapping("submit")
    public ResponseEntity<Object> uploadReview(@ModelAttribute ProductReviewFormDTO dto, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // ProductMaster와 ProductVariant 조회
        ProductMaster productMaster = this.productService.getProductById(dto.getPid());
        if (productMaster == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProductVariant productVariant;
        if (dto.getPvid() > 0) {
            productVariant = this.productVariantService.getVariantById(dto.getPvid());
            if (productVariant == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            // 기본 variant 사용
            productVariant = this.productVariantService.getVariantById(productMaster.getDefaultVariantId());
            if (productVariant == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

        List<Integer> imgId = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 이미지 처리
            if (dto.getImages() != null && !dto.getImages().isEmpty()) {
                for (int i = 0; i < dto.getImages().size(); i++) {
                    MultipartFile img = dto.getImages().get(i);
                    if (img != null && !img.isEmpty()) {
                        String filename = img.getOriginalFilename();
                        byte[] imageBytes = ImageUtil.convertToWebP(img.getInputStream());
                        MultipartFile convertedFile = ImageUtil.toMultipartFile(imageBytes, filename);
                        String name = webDAVService.uploadFile(convertedFile);
                        String[] parts = name.split("/");
                        int uploadedImgId = this.productService.uploadImg(parts[parts.length - 1]);
                        imgId.add(uploadedImgId);
                    }
                }
            }

            String json = mapper.writeValueAsString(imgId);
            
            // 답변인 경우 ReviewRecommend에 저장, 그 외에는 ProductReview에 저장
            if (dto.isAnswer()) {
                if (dto.getParentReviewId() <= 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }
                
                // 부모 리뷰 조회
                ProductReview parentReview = this.productReviewService.getProductReviewById(dto.getParentReviewId());
                if (parentReview == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                
                // ReviewRecommend 객체 생성
                ReviewRecommend reviewRecommend = ReviewRecommend.builder()
                        .writer(user)
                        .reviewId(parentReview)
                        .content(dto.getContent())
                        .userImgs(json)
                        .productId(productMaster)
                        .productVariant(productVariant)
                        .recommendCount(0)
                        .build();
                
                boolean saved = this.productReviewService.saveReviewRecommend(reviewRecommend);
                if (saved) {
                    CommentTypeDTO commentDTO = CommentTypeDTO.fromReviewRecommend(reviewRecommend);
                    return ResponseEntity.ok().body(commentDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                // 리뷰 또는 질문인 경우 ProductReview에 저장
                ProductReview productReview = ProductReview.builder()
                        .writer(user)
                        .content(dto.getContent())
                        .userImgs(json)
                        .productId(productMaster)
                        .productVariant(productVariant)
                        .recommendCount(0)
                        .reviewType(dto.getReviewType())
                        .build();
                
                boolean saved = this.productReviewService.saveProductReview(productReview);
                if (saved) {
                    CommentTypeDTO commentDTO = CommentTypeDTO.fromProductReview(productReview);
                    return ResponseEntity.ok().body(commentDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> updateReview(@ModelAttribute ProductReviewFormDTO dto, HttpSession session) {
        System.out.println("🔍 PUT /review/update 요청 수신");
        System.out.println("🔍 받은 데이터: " + dto);
        System.out.println("🔍 ID: " + dto.getId());
        System.out.println("🔍 Type: " + dto.getType());
        System.out.println("🔍 Content: " + dto.getContent());
        System.out.println("🔍 PID: " + dto.getPid());
        System.out.println("🔍 PVID: " + dto.getPvid());
        System.out.println("🔍 ParentReviewId: " + dto.getParentReviewId());
        System.out.println("🔍 Images: " + (dto.getImages() != null ? dto.getImages().size() : "null"));
        
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("🔍 사용자 로그인 정보 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // 답변인 경우 ReviewRecommend 수정
            if (dto.isAnswer()) {
                System.out.println("🔍 답글 수정 시작 - ID: " + dto.getId());
                // 답글 수정 시에는 parentReviewId가 필요하지 않으므로 조건 제거
                
                // ReviewRecommend 조회
                System.out.println("🔍 ReviewRecommend 조회 시작 - ID: " + dto.getId());
                ReviewRecommend reviewRecommend = this.productReviewService.getReviewRecommendById(dto.getId());
                if (reviewRecommend == null) {
                    System.out.println("🔍 ReviewRecommend를 찾을 수 없음 - ID: " + dto.getId());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                System.out.println("🔍 ReviewRecommend 찾음 - 작성자: " + reviewRecommend.getWriter().getEmail());
                
                // 작성자 권한 확인
                if (!reviewRecommend.getWriter().getEmail().equals(user.getEmail())) {
                    System.out.println("🔍 권한 없음 - 요청자: " + user.getEmail() + ", 작성자: " + reviewRecommend.getWriter().getEmail());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                System.out.println("🔍 권한 확인 완료");
                
                // 이미지 처리
                List<Integer> imgId = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                
                if (dto.getImages() != null && !dto.getImages().isEmpty()) {
                    for (int i = 0; i < dto.getImages().size(); i++) {
                        MultipartFile img = dto.getImages().get(i);
                        if (img != null && !img.isEmpty()) {
                            String filename = img.getOriginalFilename();
                            byte[] imageBytes = ImageUtil.convertToWebP(img.getInputStream());
                            MultipartFile convertedFile = ImageUtil.toMultipartFile(imageBytes, filename);
                            String name = webDAVService.uploadFile(convertedFile);
                            String[] parts = name.split("/");
                            int uploadedImgId = this.productService.uploadImg(parts[parts.length - 1]);
                            imgId.add(uploadedImgId);
                        }
                    }
                }
                
                String json = mapper.writeValueAsString(imgId);
                
                // ReviewRecommend 업데이트
                reviewRecommend.setContent(dto.getContent());
                reviewRecommend.setUserImgs(json);
                
                System.out.println("🔍 ReviewRecommend 업데이트 시작");
                boolean updated = this.productReviewService.updateReviewRecommend(reviewRecommend);
                if (updated) {
                    System.out.println("🔍 ReviewRecommend 업데이트 성공");
                    CommentTypeDTO commentDTO = CommentTypeDTO.fromReviewRecommend(reviewRecommend);
                    return ResponseEntity.ok().body(commentDTO);
                } else {
                    System.out.println("🔍 ReviewRecommend 업데이트 실패");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                // ProductReview 조회
                ProductReview productReview = this.productReviewService.getProductReviewById(dto.getId());
                if (productReview == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
                
                // 작성자 권한 확인
                if (!productReview.getWriter().getEmail().equals(user.getEmail())) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                
                // 이미지 처리
                List<Integer> imgId = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                
                if (dto.getImages() != null && !dto.getImages().isEmpty()) {
                    for (int i = 0; i < dto.getImages().size(); i++) {
                        MultipartFile img = dto.getImages().get(i);
                        if (img != null && !img.isEmpty()) {
                            String filename = img.getOriginalFilename();
                            byte[] imageBytes = ImageUtil.convertToWebP(img.getInputStream());
                            MultipartFile convertedFile = ImageUtil.toMultipartFile(imageBytes, filename);
                            String name = webDAVService.uploadFile(convertedFile);
                            String[] parts = name.split("/");
                            int uploadedImgId = this.productService.uploadImg(parts[parts.length - 1]);
                            imgId.add(uploadedImgId);
                        }
                    }
                }
                
                String json = mapper.writeValueAsString(imgId);
                
                // ProductReview 업데이트
                productReview.setContent(dto.getContent());
                productReview.setUserImgs(json);
                productReview.setReviewType(dto.getReviewType());
                
                boolean updated = this.productReviewService.updateProductReview(productReview);
                if (updated) {
                    CommentTypeDTO commentDTO = CommentTypeDTO.fromProductReview(productReview);
                    return ResponseEntity.ok().body(commentDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteReview(@RequestParam("id") int id, HttpSession session) {
        System.out.println("🔍 DELETE /review/delete 요청 수신 - id: " + id);
        
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            System.out.println("🔍 사용자 로그인 정보 없음");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            System.out.println("🔍 ProductReview 조회 시작 - id: " + id);
            // ProductReview 조회
            ProductReview review = this.productReviewService.getProductReviewById(id);
            if (review == null) {
                System.out.println("🔍 ProductReview를 찾을 수 없음 - id: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            System.out.println("🔍 ProductReview 찾음 - 작성자: " + review.getWriter().getEmail());

            // 작성자만 삭제 가능
            if (!review.getWriter().getEmail().equals(user.getEmail())) {
                System.out.println("🔍 권한 없음 - 요청자: " + user.getEmail() + ", 작성자: " + review.getWriter().getEmail());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            System.out.println("🔍 권한 확인 완료");

            boolean deleted = this.productReviewService.deleteProductReview(id);
            if (deleted) {
                System.out.println("🔍 삭제 성공");
                return ResponseEntity.ok("Review deleted successfully");
            } else {
                System.out.println("🔍 삭제 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            System.out.println("🔍 삭제 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("delete-recommend")
    public ResponseEntity<String> deleteReviewRecommend(@RequestParam("id") int id, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            // ReviewRecommend 조회
            ReviewRecommend reviewRecommend = this.productReviewService.getReviewRecommendById(id);
            if (reviewRecommend == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // 작성자만 삭제 가능
            if (!reviewRecommend.getWriter().getEmail().equals(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            boolean deleted = this.productReviewService.deleteReviewRecommend(id);
            if (deleted) {
                return ResponseEntity.ok("Review recommend deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
