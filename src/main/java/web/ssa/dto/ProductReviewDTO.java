package web.ssa.dto;




import lombok.Data;

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
}
