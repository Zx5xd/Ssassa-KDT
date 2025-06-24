package web.ssa.dto.products;




import lombok.Data;
import web.ssa.entity.products.ReviewRecommend;

import java.util.List;
import java.util.Map;

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
}
