package web.ssa.dto.products;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ProductReviewDTO {
    private int id;
    private String writer;
    private String content;
    private Map<String, Map<String, String>> userImgs;
    private int productId;
    private int productVariantId;
    private Integer recommendCount;
    private int reviewType;
    private List<ReviewRecommend> recommendList;

    public static List<ProductReviewDTO> convertToDTOList(List<ProductReview> entities) {
        return entities.stream()
                .map(entity -> {
                    ProductReviewDTO dto = new ProductReviewDTO();
                    dto.setId(entity.getId());
                    dto.setWriter(entity.getWriter());
                    dto.setContent(entity.getContent());
                    dto.setProductId(entity.getProductId().getId());
                    dto.setUserImgs(entity.getUserImgs());
                    dto.setRecommendCount(entity.getRecommendCount());
                    dto.setReviewType(entity.getReviewType());
                    dto.setProductVariantId(entity.getProductVariant().getId());
                    dto.setRecommendList(entity.getRecommendList());

                    return dto;
                })
                .collect(Collectors.toList());
    }

    private void setUserImgs(String userImgs) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.userImgs = objectMapper.readValue(
                    userImgs,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ProductReviewDTO{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", content='" + content + '\'' +
                ", userImgs=" + userImgs +
                ", productId=" + productId +
                ", productVariantId=" + productVariantId +
                ", recommendCount=" + recommendCount +
                ", reviewType=" + reviewType +
                ", recommendList=" + recommendList +
                '}';
    }
}
