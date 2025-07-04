package web.ssa.dto.products;

import lombok.Builder;
import lombok.Data;
import web.ssa.entity.member.User;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ProductVariant;

@Data
@Builder
public class ReviewRecommendDTO {

    private int id;
    private User writer;
    private String content;
    private String userImgs;
    private Integer recommendCount;
}

