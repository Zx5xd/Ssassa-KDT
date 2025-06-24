package web.ssa.service.categories;

import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.repository.categories.CategoryChildRepository;

import java.util.List;

public class CategoryChildServImpl implements CategoryChildServ {

    private CategoryChildRepository categoryChildRepository;

    @Override
    public List<CategoriesChild> getCategoryChild() {
        return this.categoryChildRepository.findAll();
    }

    @Override
    public List<CategoriesChild> getCategoryChild(Categories id) {
        return this.categoryChildRepository.findByCategoryChildId(id);
    }

    @Override
    public List<CategoriesChild> getCategoryChild(Categories id, String name) {
        return this.categoryChildRepository.findByCategoryChildIdAndCode(id, name);
    }
}
