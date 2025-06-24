package web.ssa.dto.categories;

import lombok.Data;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryFieldsDTO {
    private int id;
    private int categoryId;
    private String attributeKey;
    private String displayName;
    private String dataType;
    private Boolean isFilterable;
    private String unit;
    private int displayOrder;
    private String tooltip;
    private String valueList;

    public static List<CategoryFieldsDTO> convertToDTOList(List<CategoryFields> entities) {
        return entities.stream()
                .map(entity -> {
                    CategoryFieldsDTO dto = new CategoryFieldsDTO();
                    dto.setId(entity.getId());
                    dto.setCategoryId(entity.getCategoryFieldId().getId());  // categoryFieldId는 Categories 객체
                    dto.setAttributeKey(entity.getAttributeKey());
                    dto.setDisplayName(entity.getDisplayName());
                    dto.setDataType(entity.getDataType());
                    dto.setIsFilterable(entity.getIsFilterable());
                    dto.setUnit(entity.getUnit());
                    dto.setDisplayOrder(entity.getDisplayOrder());
                    dto.setTooltip(entity.getTooltip());
                    dto.setValueList(entity.getValueList());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "[ DTO ] CategoryFieldsDTO{" +
                "id=" + this.id +
                ", categoryId=" + this.categoryId +
                ", attributeKey='" + this.attributeKey + '\'' +
                ", displayName='" + this.displayName + '\'' +
                ", dataType='" + this.dataType + '\'' +
                ", isFilterable=" + this.isFilterable +
                ", unit='" + this.unit + '\'' +
                ", displayOrder=" + this.displayOrder +
                ", tooltip='" + this.tooltip + '\'' +
                ", valueList='" + this.valueList + '\'' +
                '}';
    }
}
