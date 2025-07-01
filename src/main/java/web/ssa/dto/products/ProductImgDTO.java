package web.ssa.dto.products;

import lombok.Data;
import web.ssa.dto.categories.CategoriesDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.products.ProductImg;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductImgDTO {
    private int id;
    private String imgPath;
}
