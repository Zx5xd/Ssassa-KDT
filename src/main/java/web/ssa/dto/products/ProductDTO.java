package web.ssa.dto.products;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private int categoryId;
    private int detailCategoryId;
    private int simpleImg;
    private int detailImg;
    private int price;
    private Map<String, Map<String, String>> detail;
    int defaultVariant;
    private Date reg;
}
