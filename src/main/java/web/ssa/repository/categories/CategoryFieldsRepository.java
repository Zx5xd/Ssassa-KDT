package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;

@Repository
public interface CategoryFieldsRepository extends JpaRepository<CategoryFields, Integer> {
    List<CategoryFields> findCategoryFieldsById(Integer categoryId);
    List<CategoryFields> findCategoryFieldsByDisplayName(String categoryName);
    List<CategoryFields> findCategoryFieldsByCategoryFieldId(Categories categoryFieldId);
}
