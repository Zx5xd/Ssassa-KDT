package web.ssa.dto;

import lombok.Data;

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
}
