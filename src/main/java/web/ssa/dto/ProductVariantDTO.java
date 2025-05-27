package web.ssa.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductVariantDTO {
    private int id;
    private int masterId;
    private int price;
    private String name;
    private String simpleImg;
    private String detailImg;
    private Object detail;
}
