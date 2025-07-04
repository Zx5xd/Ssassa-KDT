package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;
import web.ssa.enumf.CategoryType;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    Categories findByName(String categoryName);
    Categories findById(int id);

    Optional<Categories> findByCode(CategoryType type);

    @EntityGraph(attributePaths = {"categoryChildren"})
    @Query("SELECT c FROM Categories c LEFT JOIN FETCH c.categoryChildren")
    List<Categories> findAllWithChildren();

}
