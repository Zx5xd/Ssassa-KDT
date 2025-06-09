package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.DetailCategories;

import java.util.List;

@Repository
public interface DetailCategoryRepository extends JpaRepository<DetailCategories, Integer> {
    List<DetailCategories> findByCategoryDetailId(Categories categoryId);
    List<DetailCategories> findByCode(String categoryName);
    List<DetailCategories> findByCategoryDetailIdAndCode(Categories categoryId, String categoryName);
}
