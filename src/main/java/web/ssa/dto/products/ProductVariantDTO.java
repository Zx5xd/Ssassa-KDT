package web.ssa.dto.products;


import lombok.Data;

import java.util.Map;

@Data
public class ProductVariantDTO {
    private int id;
    private int masterId;
    private int price;
    private String name;
    private int simpleImg;
    private int detailImg;
    private Map<String, Map<String, String>> detail;
    private int amount;
}
