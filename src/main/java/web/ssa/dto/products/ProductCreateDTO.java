package web.ssa.dto.products;

import lombok.Data;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFilter;

@Data
public class ProductCreateDTO {
    private String name;
    private String strCategoryId;
    private String strCategoryChildId;
    private Integer categoryId;
    private Integer categoryChildId;
    private MultipartFile simpleImg;
    private MultipartFile detailImg;
    private Integer existingSimpleImg;
    private Integer existingDetailImg;
    private String simpleImgFileName;
    private String detailImgFileName;
    private int price;
    private String detail;
    int defaultVariant;
    private int amount;
    private int count;

    // 여러개 등록 관련 필드
    private String registrationType; // "single" 또는 "multiple"
    private List<ProductVariantDTO> variants; // 상품 변형 리스트

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date reg;

    @Override
    public String toString() {
        return "[ DTO ] ProductCreateDTO{" +
                ", name='" + name + '\'' +
                ", strCategoryId='" + strCategoryId + '\'' +
                ", strCategoryChildId='" + strCategoryChildId + '\'' +
                ", categoryId=" + categoryId +
                ", categoryChildId=" + categoryChildId +
                ", simpleImg=" + (simpleImg != null ? simpleImg.getOriginalFilename() : "null") +
                ", detailImg=" + (detailImg != null ? detailImg.getOriginalFilename() : "null") +
                ", price=" + price +
                ", detail=" + detail +
                ", amount=" + amount +
                ", registrationType=" + registrationType +
                ", variants=" + (variants != null ? variants.size() : 0) +
                ", reg=" + reg +
                '}';
    }
}
