package web.ssa.dto.products;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductReviewFormDTO {
    private String type;
    private String content;
    private int pid;
    private int pvid;
    private List<MultipartFile> images;
    private int parentReviewId; // 답변인 경우 부모 리뷰 ID
    
    // 답변인지 확인하는 메서드
    public boolean isAnswer() {
        return "answer".equalsIgnoreCase(type);
    }
    
    // ProductReview에 저장할 때 사용하는 reviewType 변환 메서드 (답변 제외)
    public int getReviewType() {
        if (type == null || type.trim().isEmpty()) {
            return 1; // 기본값: 리뷰
        }
        
        switch (type.toLowerCase()) {
            case "question":
                return 2; // 질문
            case "review":
            default:
                return 1; // 기본값: 리뷰
        }
    }
    
    @Override
    public String toString() {
        return "ProductReviewFormDTO{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", pid=" + pid +
                ", pvid=" + pvid +
                ", images=" + (images != null ? images.size() : "null") +
                ", parentReviewId=" + parentReviewId +
                '}';
    }
}
