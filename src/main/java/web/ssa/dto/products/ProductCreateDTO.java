package web.ssa.dto.products;

import lombok.Data;

import java.util.Date;

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
    private String simpleImgFileName;
    private String detailImgFileName;
    private int price;
    private String detail;
    int defaultVariant;
    private int amount;
    private int count;

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
                ", simpleImg=" + simpleImg.getOriginalFilename() +
                ", detailImg=" + detailImg.getOriginalFilename() +
                ", price=" + price +
                ", detail=" + detail +
                ", amount=" + amount +
                ", reg=" + reg +
                '}';
    }
}
