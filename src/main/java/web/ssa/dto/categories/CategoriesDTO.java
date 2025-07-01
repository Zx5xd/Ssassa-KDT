package web.ssa.dto.categories;

import lombok.Data;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoriesDTO {
    private int id;
    private String code;
    private String name;



    @Override
    public String toString() {
        return "[ DTO ] CategoriesDTO{" +
                "id=" + this.id +
                ", code='" + this.code + '\'' +
                ", name='" + this.name + '\'' +
                '}';
    }
}
