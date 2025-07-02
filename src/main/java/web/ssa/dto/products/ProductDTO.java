package web.ssa.dto.products;

import lombok.Data;
import web.ssa.entity.products.ProductMaster;
import web.ssa.util.DTOUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private int categoryId;
    private int categoryChildId;
    private int simpleImg;
    private int detailImg;
    private int price;
    private Map<String, Map<String, String>> detail;
    int defaultVariant;
    private int amount;
    private int count;
    private Date reg;
    // private int quantity;//수량

    @Override
    public String toString() {
        return "[ DTO ] ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", simpleImg=" + simpleImg +
                ", detailImg=" + detailImg +
                ", price=" + price +
                ", detail=" + detail +
                ", amount=" + amount +
                ", reg=" + reg +
                '}';
    }
}
