package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.CategoryFields;

@Repository
public interface CategoryFieldsRepository extends JpaRepository<CategoryFields, Integer> {

}
