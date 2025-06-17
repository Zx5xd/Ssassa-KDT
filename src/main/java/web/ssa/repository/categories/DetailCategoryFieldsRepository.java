package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;

import java.util.List;

@Repository
public interface DetailCategoryFieldsRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findByName(String categoryName);
    List<Categories> findById(Categories categories);
}
