package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.DetailCategories;

import java.util.List;

@Repository
public interface DetailCategoryRepository extends JpaRepository<DetailCategories, Integer> {
    List<DetailCategories> findByCategoryId(int categoryId);
    List<DetailCategories> findByCategoryName(String categoryName);
    List<DetailCategories> findByCategoryIdAndCategoryName(int categoryId, String categoryName);
}
