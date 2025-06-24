package web.ssa.service.categories;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ssa.entity.categories.Categories;
import web.ssa.repository.categories.CategoryFieldsRepository;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryFieldsRepository categoryFieldsRepository;

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



    // Category Fields Service
    @Transactional
    public void reorderField(int categoryId, int childId, String attributeKey, int oldOrder, int newOrder) {
        if (oldOrder > newOrder) {
            categoryFieldsRepository.shiftDown(categoryId, childId, newOrder, oldOrder);
        } else if (oldOrder < newOrder) {
            categoryFieldsRepository.shiftUp(categoryId, childId, oldOrder, newOrder);
        }

        categoryFieldsRepository.updateOrder(categoryId, attributeKey, newOrder);
    }
}
