package web.ssa.service.categories;

import org.springframework.stereotype.Service;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.entity.categories.CategoriesChild;

import java.util.List;

@Service
public interface CategoryFieldServ {
    List<CategoryFieldsDTO> getCategoryFieldsByCategoryId(int id);

    List<CategoryFieldsDTO> getCategoryFieldsByChildId(int categoryId, List<CategoriesChild> childId);

    List<CategoryFieldsDTO> getCategoryFieldsByChildId(int categoryId, int childId);

    CategoryFieldsDTO getCategoryFieldById(int fieldId);

    void updateValueList(int fieldId, String newValueList);
}
