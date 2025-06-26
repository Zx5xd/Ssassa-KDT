package web.ssa.dto.products;

import lombok.Data;
import web.ssa.entity.products.ProductMaster;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private int amount;
    private int count;
    private Date reg;
    private int quantity;//수량

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

    public static List<ProductDTO> convertToDTOList(List<ProductMaster> entities) {
        return entities.stream()
                .map(entity -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setCategoryId(entity.getCategoryId());
                    dto.setDetailCategoryId(entity.getCategoryId());
                    dto.setSimpleImg(entity.getSimpleImg());
                    dto.setDetailImg(entity.getDetailImg());
                    dto.setPrice(entity.getPrice());
                    dto.setAmount(entity.getAmount());
                    dto.setReg(entity.getReg());

                    return dto;
                })
                .collect(Collectors.toList());
    }


}
