package web.ssa.service.categories;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ssa.dto.categories.CategoriesDTO;
import web.ssa.dto.categories.PLCategoryDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.repository.categories.CategoryFieldsRepository;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Map<Integer, PLCategoryDTO> getCategoryMap() {
        // EntityGraph(fetch join)를 통해 카테고리 + 자식 카테고리 동시 로딩
        List<Categories> categories = categoryRepository.findAllWithChildren();

        // DTO 변환 및 Map 구조로 변환
        return categories.stream()
                .collect(Collectors.toMap(
                        Categories::getId,     // keyMapper
                        PLCategoryDTO::from    // valueMapper
                ));
    }
}
