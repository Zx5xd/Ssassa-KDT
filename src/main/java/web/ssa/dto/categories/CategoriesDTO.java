package web.ssa.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CategoriesDTO {
    private int id;
    private String code;
    private String name;

    public static CategoriesDTO from(Categories categories) {
        return new  CategoriesDTO()
                .setId(categories.getId())
                .setCode(categories.getCode().getDisplayName())
                .setName(categories.getName());
    }

    public static CategoriesDTO fromChild(CategoriesChild categories) {
        return new  CategoriesDTO()
                .setId(categories.getId())
                .setCode(categories.getCode())
                .setName(categories.getName());
    }
}
