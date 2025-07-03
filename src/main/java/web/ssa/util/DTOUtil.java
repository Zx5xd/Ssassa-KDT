package web.ssa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import web.ssa.dto.categories.CategoryFieldsDTO;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class DTOUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, Map<String, String>> stringToMapping(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    str,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> stringToMap(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    str,
                    new TypeReference<Map<String, String>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String mappingToString(Map<String, Map<String, String>> map) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * JSON 형태의 valueList를 HTML 테이블 형태로 가공
     */
    public String formatValueList(String jsonValue) {
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

    /**
     * valueList JSON 배열을 Map으로 변환 (value를 key로, weight를 value로)
     * 예시: [{"value": "422", "weight": 1}, {"value": "N/A", "weight": 2}]
     * → {"422": "1", "N/A": "2"}
     */
    public Map<String, String> valueListToMap(String valueListJson) {
        if (valueListJson == null || valueListJson.trim().isEmpty()) {
            return Map.of();
        }

        try {
            JsonNode node = objectMapper.readTree(valueListJson);
            Map<String, String> resultMap = new java.util.HashMap<>();

            if (node.isArray()) {
                StreamSupport.stream(node.spliterator(), false)
                        .forEach(item -> {
                            if (item.has("value") && item.has("weight")) {
                                String value = item.get("value").asText();
                                String weight = item.get("weight").asText();
                                resultMap.put(value, weight);
                            }
                        });
            }

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of();
        }
    }

    /**
     * valueList JSON 배열을 weight 기준으로 정렬된 Map으로 변환
     * 예시: [{"value": "422", "weight": 1}, {"value": "N/A", "weight": 2}]
     * → {"422": "1", "N/A": "2"} (weight 순으로 정렬)
     */
    public Map<String, String> valueListToSortedMap(String valueListJson) {
        Map<String, String> map = valueListToMap(valueListJson);

        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((v1, v2) -> {
                    try {
                        return Integer.compare(Integer.parseInt(v1), Integer.parseInt(v2));
                    } catch (NumberFormatException e) {
                        return v1.compareTo(v2);
                    }
                }))
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        java.util.LinkedHashMap::new));
    }

    /**
     * CategoryFieldsDTO 리스트를 DisplayOrder 순으로 정렬 (오름차순)
     * 
     * @param categoryFieldsList 정렬할 CategoryFieldsDTO 리스트
     * @return DisplayOrder 순으로 정렬된 리스트
     */
    public List<CategoryFieldsDTO> sortCategoryFieldsByDisplayOrder(List<CategoryFieldsDTO> categoryFieldsList) {
        if (categoryFieldsList == null || categoryFieldsList.isEmpty()) {
            return categoryFieldsList;
        }

        return categoryFieldsList.stream()
                .sorted((dto1, dto2) -> {
                    int order1 = dto1.getDisplayOrder();
                    int order2 = dto2.getDisplayOrder();
                    return Integer.compare(order1, order2);
                })
                .toList();
    }

    /**
     * CategoryFieldsDTO 리스트를 DisplayOrder 순으로 정렬 (내림차순)
     * 
     * @param categoryFieldsList 정렬할 CategoryFieldsDTO 리스트
     * @return DisplayOrder 순으로 정렬된 리스트 (내림차순)
     */
    public List<CategoryFieldsDTO> sortCategoryFieldsByDisplayOrderDesc(List<CategoryFieldsDTO> categoryFieldsList) {
        if (categoryFieldsList == null || categoryFieldsList.isEmpty()) {
            return categoryFieldsList;
        }

        return categoryFieldsList.stream()
                .sorted((dto1, dto2) -> {
                    int order1 = dto1.getDisplayOrder();
                    int order2 = dto2.getDisplayOrder();
                    return Integer.compare(order2, order1); // 내림차순
                })
                .toList();
    }

    /**
     * CategoryFieldsDTO 리스트를 DisplayOrder 순으로 정렬 (null 값 처리 포함)
     * 
     * @param categoryFieldsList 정렬할 CategoryFieldsDTO 리스트
     * @param nullFirst          null 값을 먼저 정렬할지 여부 (true: null 먼저, false: null 나중에)
     * @return DisplayOrder 순으로 정렬된 리스트
     */
    public List<CategoryFieldsDTO> sortCategoryFieldsByDisplayOrder(List<CategoryFieldsDTO> categoryFieldsList,
            boolean nullFirst) {
        if (categoryFieldsList == null || categoryFieldsList.isEmpty()) {
            return categoryFieldsList;
        }

        return categoryFieldsList.stream()
                .sorted((dto1, dto2) -> {
                    Integer order1 = dto1.getDisplayOrder();
                    Integer order2 = dto2.getDisplayOrder();

                    // null 값 처리
                    if (order1 == null && order2 == null) {
                        return 0;
                    }
                    if (order1 == null) {
                        return nullFirst ? -1 : 1;
                    }
                    if (order2 == null) {
                        return nullFirst ? 1 : -1;
                    }

                    return Integer.compare(order1, order2);
                })
                .toList();
    }

    /**
     * CategoryFieldsDTO 리스트를 DisplayOrder 순으로 정렬하고 새로운 List로 반환
     * 
     * @param categoryFieldsList 정렬할 CategoryFieldsDTO 리스트
     * @return DisplayOrder 순으로 정렬된 새로운 ArrayList
     */
    public ArrayList<CategoryFieldsDTO> sortCategoryFieldsByDisplayOrderToArrayList(
            List<CategoryFieldsDTO> categoryFieldsList) {
        if (categoryFieldsList == null || categoryFieldsList.isEmpty()) {
            return new java.util.ArrayList<>();
        }

        return categoryFieldsList.stream()
                .sorted((dto1, dto2) -> {
                    int order1 = dto1.getDisplayOrder();
                    int order2 = dto2.getDisplayOrder();
                    return Integer.compare(order1, order2);
                })
                .collect(Collectors.toCollection(java.util.ArrayList::new));
    }

    /**
     * valueList에서 value 값들을 추출하여 리스트로 반환
     * 
     * @param valueListJson valueList JSON 문자열
     * @return value 값들의 리스트 (weight 순으로 정렬됨)
     */
    public List<String> extractValuesFromValueList(String valueListJson) {
        Map<String, String> valueWeightMap = valueListToSortedMap(valueListJson);
        return new ArrayList<>(valueWeightMap.keySet());
    }

    /**
     * 숫자 타입의 value에 unit을 결합하여 문자열로 반환
     * 
     * @param value 값
     * @param unit  단위
     * @return value + unit 문자열
     */
    public String combineValueWithUnit(String value, String unit) {
        if (value == null || value.trim().isEmpty()) {
            return "";
        }

        // 숫자인지 확인 (int/float)
        if (isNumeric(value)) {
            if (unit != null && !unit.trim().isEmpty()) {
                return value + unit;
            } else {
                return value;
            }
        } else {
            // 숫자가 아니면 value만 반환
            return value;
        }
    }

    /**
     * 문자열이 숫자인지 확인 (int/float)
     * 
     * @param str 확인할 문자열
     * @return 숫자이면 true, 아니면 false
     */
    private boolean isNumeric(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }

        try {
            // 정수 확인
            Integer.parseInt(str.trim());
            return true;
        } catch (NumberFormatException e1) {
            try {
                // 실수 확인
                Float.parseFloat(str.trim());
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }

    /**
     * CategoryFieldsDTO 리스트를 필터링하여 단순 Map 형태로 처리 (값만 추출)
     * 
     * @param categoryFieldsList 처리할 CategoryFieldsDTO 리스트
     * @return Map<필드명, List<처리된값>> 형태로 처리된 데이터
     */
    public Map<String, List<String>> processCategoryFieldsForFilterAsSimpleMap(
            List<CategoryFieldsDTO> categoryFieldsList) {
        if (categoryFieldsList == null || categoryFieldsList.isEmpty()) {
            return Map.of();
        }

        Map<String, List<String>> resultMap = new java.util.HashMap<>();

        categoryFieldsList.stream()
                .filter(dto -> dto.getValueList() != null && !dto.getValueList().trim().isEmpty())
                .forEach(dto -> {
                    // valueList를 weight 순으로 정렬된 Map으로 변환
                    Map<String, String> valueWeightMap = valueListToSortedMap(dto.getValueList());

                    // 각 value에 대해 unit 결합 처리하여 리스트로 변환
                    List<String> processedValues = valueWeightMap.keySet().stream()
                            .map(value -> combineValueWithUnit(value, dto.getUnit()))
                            .collect(Collectors.toList());

                    resultMap.put(dto.getDisplayName(), processedValues);
                });

        return resultMap;
    }

    /**
     * 값 리스트를 JSON 형태로 변환
     * 
     * @param values 변환할 값 리스트
     * @return JSON 형태의 문자열
     */
    private String convertValuesToJson(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "[]";
        }

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0)
                jsonBuilder.append(",");
            jsonBuilder.append("{\"value\":\"").append(values.get(i)).append("\",\"weight\":").append(i + 1)
                    .append("}");
        }
        jsonBuilder.append("]");

        return jsonBuilder.toString();
    }
}
