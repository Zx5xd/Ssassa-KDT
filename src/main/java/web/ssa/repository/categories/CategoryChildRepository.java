package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;

import java.util.List;

@Repository
public interface CategoryChildRepository extends JpaRepository<CategoriesChild, Integer> {
    List<CategoriesChild> findByCategoryChildId(Categories categoryId);
    List<CategoriesChild> findByCode(String categoryName);
    List<CategoriesChild> findByCategoryChildIdAndCode(Categories categoryId, String categoryName);
}
