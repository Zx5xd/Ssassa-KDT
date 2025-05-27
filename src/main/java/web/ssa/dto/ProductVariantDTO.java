package web.ssa.dto;


import lombok.Data;

import java.util.Map;

@Data
public class ProductVariantDTO {
    private int id;
    private int masterId;
    private int price;
    private String name;
    private String simpleImg;
    private String detailImg;
    private Map<String, Map<String, String>> detail;
}
