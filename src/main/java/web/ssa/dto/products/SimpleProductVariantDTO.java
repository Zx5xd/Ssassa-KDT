package web.ssa.dto.products;


import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import web.ssa.entity.products.ProductVariant;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SimpleProductVariantDTO {
    private int id;
    private int masterId;
    private int price;
    private String name;
    private int simpleImg;
    private int detailImg;

    @JsonRawValue
    private String detail;

    public static SimpleProductVariantDTO from(ProductVariant variant) {
        return new SimpleProductVariantDTO()
                .setId(variant.getId())
                .setMasterId(variant.getMasterId().getId())
                .setPrice(variant.getPrice())
                .setName(variant.getName())
                .setSimpleImg(variant.getSimpleImg())
                .setDetailImg(variant.getDetailImg())
                .setDetail(variant.getDetail());
    }
}
