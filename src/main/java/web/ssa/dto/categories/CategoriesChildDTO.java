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

    public static List<CategoriesChildDTO> convertToDTOList(List<CategoriesChild> entities) {
        return entities.stream()
                .map(entity -> {
                    CategoriesChildDTO dto = new CategoriesChildDTO();
                    dto.setId(entity.getId());
                    dto.setCode(String.valueOf(entity.getCode()));
                    dto.setName(entity.getName());
                    dto.setCategoryId(entity.getCategoryChildId().getId());

                    return dto;
                })
                .collect(Collectors.toList());
    }

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
