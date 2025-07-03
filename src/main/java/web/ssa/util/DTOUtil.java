package web.ssa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
}
