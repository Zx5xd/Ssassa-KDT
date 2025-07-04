package web.ssa.service.categories;

import org.springframework.stereotype.Service;
import web.ssa.dto.categories.PLCategoryDTO;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.categories.CategoryFields;

import java.util.List;
import java.util.Map;

@Service
public interface CategoryService {
    List<Categories> getCategories();
    Categories getCategoryById(int id);
    Categories getCategoryByName(String name);

    Map<Integer, PLCategoryDTO> getCategoryMap();

}
