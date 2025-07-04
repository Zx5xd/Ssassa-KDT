package web.ssa.dto.products;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentTypeDTO {
    private int id;
    private String author;
    private String userId;
    private String content;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String createdAt;
    private List<String> images;
    private List<CommentTypeDTO> replies;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // ProductReview에서 CommentTypeDTO로 변환
    public static CommentTypeDTO fromProductReview(ProductReview review) {
        CommentTypeDTO dto = new CommentTypeDTO();
        dto.setId(review.getId());
        dto.setAuthor(review.getWriter().getNickname());
        dto.setUserId(review.getWriter().getEmail());
        dto.setContent(review.getContent());
        
        // reviewType을 type으로 변환
        switch (review.getReviewType()) {
            case 1:
                dto.setType("review");
                break;
            case 2:
                dto.setType("question");
                break;
            default:
                dto.setType("review");
        }
        
        dto.setCreatedAt(review.getCreated().toString());
        
        // images 처리 (JSON 문자열을 List로 변환)
        dto.setImages(parseImages(review.getUserImgs()));
        
        // replies 처리 (ReviewRecommend를 CommentTypeDTO로 변환)
        if (review.getRecommendList() != null) {
            dto.setReplies(review.getRecommendList().stream()
                    .map(CommentTypeDTO::fromReviewRecommend)
                    .collect(Collectors.toList()));
        } else {
            dto.setReplies(List.of());
        }
        
        return dto;
    }

    // ReviewRecommend에서 CommentTypeDTO로 변환
    public static CommentTypeDTO fromReviewRecommend(ReviewRecommend recommend) {
        CommentTypeDTO dto = new CommentTypeDTO();
        dto.setId(recommend.getId());
        dto.setAuthor(recommend.getWriter().getNickname());
        dto.setUserId(recommend.getWriter().getEmail());
        dto.setContent(recommend.getContent());
        dto.setType("answer");
        dto.setCreatedAt(recommend.getCreated().toString());
        
        // images 처리
        dto.setImages(parseImages(recommend.getUserImgs()));
        
        // 답변은 replies가 없음
        dto.setReplies(List.of());
        
        return dto;
    }

    // 이미지 JSON 문자열을 파싱하는 헬퍼 메서드
    private static List<String> parseImages(String userImgs) {
        if (userImgs == null || userImgs.isEmpty()) {
            return List.of();
        }
        
        try {
            // JSON 배열을 파싱하여 이미지 ID 리스트로 변환
            List<Integer> imageIds = objectMapper.readValue(userImgs, new TypeReference<List<Integer>>() {});
            // 실제로는 이미지 ID를 URL로 변환해야 함
            return imageIds.stream()
                    .map(id -> "http://localhost:8080/pdr/img/" + id) // 임시 URL 형식
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }
} 