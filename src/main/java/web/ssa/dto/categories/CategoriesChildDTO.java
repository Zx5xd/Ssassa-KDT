package web.ssa.dto.categories;

import lombok.Data;
import web.ssa.entity.categories.CategoriesChild;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoriesChildDTO {
    private int id;
    private int categoryId;
    private String code;
    private String name;



    @Override
    public String toString() {
        return "[ DTO ]CategoriesChild{" +
                "id=" + this.id +
                ", categoryId=" + this.categoryId +
                ", code='" + this.code + '\'' +
                ", name='" + this.name + '\'' +
                '}';
    }
}
