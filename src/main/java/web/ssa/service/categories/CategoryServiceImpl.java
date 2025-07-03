package web.ssa.service.categories;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.DisplayOrderDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.categories.CategoryFields;
import web.ssa.mapper.ConvertToDTO;
import web.ssa.repository.categories.CategoryFieldsRepository;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryFieldsRepository categoryFieldsRepository;
    @Autowired
    private View error;

    @Override
    public List<Categories> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Categories getCategoryById(int id) {
        return this.categoryRepository.findById(id);
    }

    @Override
    public Categories getCategoryByName(String name) {
        return this.categoryRepository.findByName(name);
    }

}
