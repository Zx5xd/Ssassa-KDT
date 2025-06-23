package web.ssa.dto.categories;

import lombok.Data;

@Data
public class CategoriesChild {
    private int id;
    private int categoryId;
    private String code;
    private String name;
}
