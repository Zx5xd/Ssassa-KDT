package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    Categories findByName(String categoryName);
    Categories findById(int id);
}
