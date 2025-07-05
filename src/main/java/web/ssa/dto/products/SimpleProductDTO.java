package web.ssa.dto.products;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import web.ssa.entity.products.ProductMaster;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SimpleProductDTO {
    private int id;
    private String name;
    private int categoryId;
    private int categoryChildId;
    private int simpleImg;
    private int price;
    @JsonRawValue
    private String detail;
    int defaultVariant;
    private Date reg;
    private List<SimpleProductVariantDTO> variants;

    public static SimpleProductDTO from(ProductMaster product) {
        return new SimpleProductDTO()
                .setId(product.getId())
                .setName(product.getName())
                .setCategoryId(product.getCategoryId())
                .setCategoryChildId(product.getCategoryChildId())
                .setSimpleImg(product.getSimpleImg())
                .setPrice(product.getPrice())
                .setDetail(product.getDetail())
                .setReg(product.getReg())
                .setDefaultVariant(product.getDefaultVariantId())
                .setVariants(
                        product.getVariants() != null
                                ? product.getVariants().stream().map(SimpleProductVariantDTO::from).toList()
                                : List.of());
    }
}
