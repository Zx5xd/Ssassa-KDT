package web.ssa.service.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ssa.entity.categories.Categories;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Categories> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Categories getCategoryById(int id) {
        return this.categoryRepository.findByCategoryId(id);
    }

    @Override
    public Categories getCategoryByName(String name) {
        return this.categoryRepository.findByCategoryName(name);
    }
}
