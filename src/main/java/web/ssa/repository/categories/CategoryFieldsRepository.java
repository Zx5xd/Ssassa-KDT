package web.ssa.repository.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;

@Repository
public interface CategoryFieldsRepository extends JpaRepository<CategoryFields, Integer> {
        List<CategoryFields> findByCategoryFieldId_Id(Integer categoryId);

        List<CategoryFields> findCategoryFieldsByDisplayName(String categoryName);

        List<CategoryFields> findByCategoryFieldId_IdAndCategoryChildId_Id(int categoryId, int categoryChildId);

        @Query("SELECT cf FROM CategoryFields cf WHERE cf.categoryFieldId.id = :categoryId AND cf.categoryChildId IN :childs")
        List<CategoryFields> findByCategoryAndChilds(@Param("categoryId") int categoryId,
                        @Param("childs") List<CategoriesChild> childs);

        @Modifying
        @Query(value = """
                            UPDATE category_fields
                            SET display_order = display_order + 1
                            WHERE category_id = :categoryId
                              AND (category_child_id = :childId OR (category_child_id IS NULL AND :childId IS NULL))
                              AND display_order >= :newOrder AND display_order < :oldOrder
                        """, nativeQuery = true)
        void shiftDown(int categoryId, Integer childId, int newOrder, int oldOrder);

        @Modifying
        @Query(value = """
                            UPDATE category_fields
                            SET display_order = display_order - 1
                            WHERE category_id = :categoryId
                              AND (category_child_id = :childId OR (category_child_id IS NULL AND :childId IS NULL))
                              AND display_order <= :newOrder AND display_order > :oldOrder
                        """, nativeQuery = true)
        void shiftUp(int categoryId, Integer childId, int oldOrder, int newOrder);

        @Modifying
        @Query(value = "UPDATE category_fields SET display_order = ?2 WHERE id = ?1", nativeQuery = true)
        void updateOrder(Integer fieldId, int newOrder);

        @Modifying
        @Query(value = """
                            UPDATE category_fields
                            SET display_order = :newOrder
                            WHERE id = :fieldId
                        """, nativeQuery = true)
        void updateOrders(@Param("fieldId") int fieldId, @Param("newOrder") int newOrder);
}
