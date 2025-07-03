package web.ssa.dto.products;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductReviewFormDTO {
    private String type;
    private String content;
    private int pid;
    private int pvid;
    private List<MultipartFile> images;
}
