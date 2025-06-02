package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;

@Repository
public interface CategoryFieldsRepository extends JpaRepository<CategoryFields, Integer> {
    List<CategoryFields> findByCategoryId(Integer categoryId);
    List<CategoryFields> findByCategoryName(String categoryName);
    List<CategoryFields> findByCategoryIdAndCategoryName(Integer categoryId, String categoryName);
}
