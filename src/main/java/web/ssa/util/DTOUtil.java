package web.ssa.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class DTOUtil {

    public Map<String, Map<String, String>> stringToMapping(String str) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(
                    str,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
