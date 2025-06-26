package web.ssa.service.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.repository.categories.CategoryChildRepository;

import java.util.List;

@Service
public class CategoryChildServImpl implements CategoryChildServ {

    @Autowired
    private CategoryChildRepository categoryChildRepository;

    @Override
    public List<CategoriesChild> getCategoryChild() {
        return this.categoryChildRepository.findAll();
    }

    @Override
    public List<CategoriesChild> getCategoryChild(Categories id) {
        return this.categoryChildRepository.findByCategory(id);
    }

    @Override
    public List<CategoriesChild> getCategoryChild(Categories id, String name) {
        return this.categoryChildRepository.findByCategoryAndCode(id, name);
    }
}
