package web.ssa.dto.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import web.ssa.entity.products.ProductVariant;
import org.springframework.web.multipart.MultipartFile;

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
    private Integer existingDetailImg;
    private Integer existingSimpleImg;
    private Map<String, Map<String, String>> detail;
    private int amount;

    // 파일 업로드 관련 필드
    private MultipartFile detailImgFile;
    private String detailImgFileName;

    // detail 필드를 String으로도 받을 수 있도록
    private String detailString;

    public void setDetail(String detail) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.detail = objectMapper.readValue(
                    detail,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDetailString(String detailString) {
        this.detailString = detailString;
        if (detailString != null && !detailString.trim().isEmpty()) {
            setDetail(detailString);
        }
    }

    @Override
    public String toString() {
        return "ProductVariantDTO{" +
                "id=" + id +
                ", masterId=" + masterId +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", simpleImg=" + simpleImg +
                ", detailImg=" + detailImg +
                ", detail=" + detail +
                ", amount=" + amount +
                ", existingDetailImg=" + existingDetailImg +
                ", detailImgFile=" + (detailImgFile != null ? detailImgFile.getOriginalFilename() : "null") +
                ", detailImgFileName='" + detailImgFileName + '\'' +
                ", detailString='" + detailString + '\'' +
                '}';
    }
}
