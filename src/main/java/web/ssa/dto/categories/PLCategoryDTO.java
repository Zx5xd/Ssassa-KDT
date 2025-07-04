package web.ssa.dto.categories;

import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;

import java.util.Map;
import java.util.stream.Collectors;

public record PLCategoryDTO(
        int id,
        String code,
        String name,
        Map<Integer, CategoriesDTO> variants
) {
    public static PLCategoryDTO from(Categories entity) {
        Map<Integer, CategoriesDTO> variants = entity.getCategoryChildren() != null
                ? entity.getCategoryChildren().stream()
                .collect(Collectors.toMap(
                        CategoriesChild::getId,
                        CategoriesDTO::fromChild
                ))
                : Map.of(); // 빈 Map

        return new PLCategoryDTO(
                entity.getId(),
                entity.getCode().name(),
                entity.getName(),
                variants
        );
    }
}
