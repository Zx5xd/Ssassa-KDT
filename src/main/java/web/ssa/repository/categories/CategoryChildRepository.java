package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;

import java.util.List;

@Repository
public interface CategoryChildRepository extends JpaRepository<CategoriesChild, Integer> {
    List<CategoriesChild> findByCategory(Categories category);  // ✅ 필드명 category로 수정
    List<CategoriesChild> findByCode(String code);
    List<CategoriesChild> findByCategoryAndCode(Categories category, String code);  // ✅ 필드명 category 사용
}
