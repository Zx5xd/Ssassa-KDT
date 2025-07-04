package web.ssa.dto.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.member.User;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ReviewRecommend;
import web.ssa.util.DTOUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ProductReviewDTO {
    private int id;
    private User writer;
    private String content;
    private Map<String, Map<String, String>> userImgs;
    private int productId;
    private int productVariantId;
    private Integer recommendCount;
    private int reviewType;
    private List<ReviewRecommend> recommendList;

    // private void setUserImgs(String userImgs) {
    // ObjectMapper objectMapper = new ObjectMapper();
    // try {
    // this.userImgs = objectMapper.readValue(
    // userImgs,
    // new TypeReference<Map<String, Map<String, String>>>() {
    // }
    // );
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    @Override
    public String toString() {
        return "ProductReviewDTO{" +
                "id=" + id +
                ", writer='" + writer.getNickname() + '\'' +
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
