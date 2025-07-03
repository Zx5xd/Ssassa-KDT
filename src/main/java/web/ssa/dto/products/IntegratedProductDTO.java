package web.ssa.dto.products;

import com.fasterxml.jackson.annotation.JsonRawValue;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductVariant;

public record IntegratedProductDTO(
        int id,
        int variantId,
        int price,
        String name,
        int img,
        @JsonRawValue String detail
) {
    public static IntegratedProductDTO from(ProductMaster product) {
        return new IntegratedProductDTO(
                product.getId(),
                -1,
                product.getPrice(),
                product.getName(),
                product.getDetailImg(),
                product.getDetail()
        );
    }

    public static IntegratedProductDTO from(ProductVariant product) {
        return new IntegratedProductDTO(
                product.getMasterId().getId(),
                product.getId(),
                product.getPrice(),
                product.getName(),
                product.getDetailImg(),
                product.getDetail()
        );
    }
}

