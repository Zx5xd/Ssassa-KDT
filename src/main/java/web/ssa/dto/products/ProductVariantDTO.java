package web.ssa.dto.products;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.products.ProductReview;
import web.ssa.entity.products.ProductVariant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void setDetail(String detail) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.detail = objectMapper.readValue(
                    detail,
                    new TypeReference<Map<String, Map<String, String>>>() {}
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ProductVariantDTO> convertToDTOList(List<ProductVariant> entities) {
        return entities.stream()
                .map(entity -> {
                    ProductVariantDTO dto = new ProductVariantDTO();
                    dto.setId(entity.getId());
                    dto.setName(entity.getName());
                    dto.setPrice(entity.getPrice());
                    dto.setDetail(entity.getDetail());
                    dto.setAmount(entity.getAmount());
                    dto.setSimpleImg(entity.getSimpleImg());
                    dto.setDetailImg(entity.getDetailImg());
                    dto.setMasterId(entity.getMasterId().getId());

                    return dto;
                })
                .collect(Collectors.toList());
    }
}
