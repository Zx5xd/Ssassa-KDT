package web.ssa.dto.categories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
public class CategoryFieldsDTO {
    private int id;
    private int categoryId;
    private int categoryChildId;
    private String attributeKey;
    private String displayName;
    private String dataType;
    private Boolean isFilterable;
    private String unit;
    private int displayOrder;
    private String tooltip;
    private String valueList;
    private String formattedValue; // 가공된 value / weight 형태
    private Map<String, List<String>> valueListMap;

    @Override
    public String toString() {
        return "[ DTO ] CategoryFieldsDTO{" +
                "id=" + this.id +
                ", categoryId=" + this.categoryId +
                ", categoryChildId=" + this.categoryChildId +
                ", attributeKey='" + this.attributeKey + '\'' +
                ", displayName='" + this.displayName + '\'' +
                ", dataType='" + this.dataType + '\'' +
                ", isFilterable=" + this.isFilterable +
                ", unit='" + this.unit + '\'' +
                ", displayOrder=" + this.displayOrder +
                ", tooltip='" + this.tooltip + '\'' +
                ", valueList='" + this.valueList + '\'' +
                ", formattedValue='" + this.formattedValue + '\'' +
                ", valueListMap=" + this.valueListMap +
                '}';
    }
}
