package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.DetailCategories;

@Repository
public interface DetailCategoryRepository extends JpaRepository<DetailCategories, Integer> {

}
