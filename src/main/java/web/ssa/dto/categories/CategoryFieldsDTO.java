package web.ssa.dto.categories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<CategoryFieldsDTO> convertToDTOList(List<CategoryFields> entities) {
        return entities.stream()
                .map(entity -> {
                    CategoryFieldsDTO dto = new CategoryFieldsDTO();
                    dto.setId(entity.getId());
                    dto.setCategoryId(entity.getCategoryFieldId().getId()); // categoryFieldId는 Categories 객체
                    if (entity.getCategoryChildId() != null) {
                        dto.setCategoryChildId(entity.getCategoryChildId().getId());
                    } else {
                        dto.setCategoryChildId(0);
                    }
                    dto.setAttributeKey(entity.getAttributeKey());
                    dto.setDisplayName(entity.getDisplayName());
                    dto.setDataType(entity.getDataType());
                    dto.setIsFilterable(entity.getIsFilterable());
                    dto.setUnit(entity.getUnit());
                    dto.setDisplayOrder(entity.getDisplayOrder());
                    dto.setTooltip(entity.getTooltip());
                    dto.setValueList(entity.getValueList());
                    dto.setFormattedValue(formatValueList(entity.getValueList()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * JSON 형태의 valueList를 HTML 테이블 형태로 가공
     */
    private static String formatValueList(String jsonValue) {
        if (jsonValue == null || jsonValue.trim().isEmpty()) {
            return "";
        }

        try {
            JsonNode node = objectMapper.readTree(jsonValue);
            return extractValueWeightAsTable(node);
        } catch (Exception e) {
            // JSON 파싱 실패시 원본 반환
            return jsonValue;
        }
    }

    /**
     * JsonNode에서 value/weight 정보를 HTML 테이블로 추출
     */
    private static String extractValueWeightAsTable(JsonNode node) {
        StringBuilder tableHtml = new StringBuilder();
        tableHtml.append("<table class='value-weight-table'>");
        tableHtml.append("<thead><tr>");
        tableHtml.append("<th>Value</th>");
        tableHtml.append("<th>Weight</th>");
        tableHtml.append("</tr></thead><tbody>");
        // tableHtml.append("<tbody>");

        // 단일 객체인 경우
        if (node.has("value") && node.has("weight")) {
            String value = node.get("value").asText();
            String weight = node.get("weight").asText();
            tableHtml.append("<tr>");
            tableHtml.append("<td>").append(value).append("</td>");
            tableHtml.append("<td>").append(weight).append("</td>");
            tableHtml.append("</tr>");
        }
        // 배열인 경우
        else if (node.isArray()) {
            StreamSupport.stream(node.spliterator(), false)
                    .forEach(item -> {
                        if (item.has("value") && item.has("weight")) {
                            String value = item.get("value").asText();
                            String weight = item.get("weight").asText();
                            // tableHtml.append("<tr>");
                            tableHtml.append("<td>").append(value).append("</td>");
                            tableHtml.append("<td>").append(weight).append("</td>");
                            // tableHtml.append("</tr>");
                        }
                    });
        }

        tableHtml.append("</tbody></table>");
        return tableHtml.toString();
    }

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
                '}';
    }
}
