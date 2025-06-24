package web.ssa.service.categories;

import org.springframework.stereotype.Service;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;

import java.util.List;

@Service
public interface CategoryChildServ {
    List<CategoriesChild> getCategoryChild();
    List<CategoriesChild> getCategoryChild(Categories categoriesId);
    List<CategoriesChild> getCategoryChild(Categories categoriesId, String name);
}
