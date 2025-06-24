package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;

@Repository
public interface CategoryFieldsRepository extends JpaRepository<CategoryFields, Integer> {
    List<CategoryFields> findCategoryFieldsById(Integer categoryId);
    List<CategoryFields> findCategoryFieldsByDisplayName(String categoryName);
    List<CategoryFields> findCategoryFieldsByCategoryFieldId(Categories categoryFieldId);

    @Modifying
    @Query(value = """
        UPDATE category_fields 
        SET display_order = display_order + 1 
        WHERE category_id = :catId 
          AND (category_child_id = :childId OR (category_child_id IS NULL AND :childId IS NULL))
          AND display_order >= :newOrder AND display_order < :oldOrder
    """, nativeQuery = true)
    void shiftDown(int catId, int childId, int newOrder, int oldOrder);

    @Modifying
    @Query(value = """
        UPDATE category_fields 
        SET display_order = display_order - 1 
        WHERE category_id = :catId 
          AND (category_child_id = :childId OR (category_child_id IS NULL AND :childId IS NULL))
          AND display_order <= :newOrder AND display_order > :oldOrder
    """, nativeQuery = true)
    void shiftUp(int catId, int childId, int oldOrder, int newOrder);

    @Modifying
    @Query(value = """
        UPDATE category_fields 
        SET display_order = :newOrder 
        WHERE category_id = :catId 
          AND attribute_key = :key
    """, nativeQuery = true)
    void updateOrder(int catId, String key, int newOrder);
}
