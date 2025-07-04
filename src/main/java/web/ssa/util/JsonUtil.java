package web.ssa.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Map<String, String>> toMap(String json) {
        try {
            JavaType innerMapType = mapper.getTypeFactory()
                    .constructMapType(Map.class, String.class, String.class);
            JavaType outerMapType = mapper.getTypeFactory()
                    .constructMapType(Map.class, String.class, innerMapType.getRawClass());
            return mapper.readValue(json, outerMapType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    public static String toJson(Map<String, Map<String, String>> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}


