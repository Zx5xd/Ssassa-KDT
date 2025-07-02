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

    @Override
    public List<CategoryFieldsDTO> getCategoryFieldsByCategoryId(int id) {
        return ConvertToDTO.categoryFieldsDTOList(
                this.categoryFieldsRepository.findByCategoryFieldId_Id(id));
    }

    @Override
    public List<CategoryFieldsDTO> getCategoryFieldsByChildId(int categoryId, List<CategoriesChild> childId) {
        return ConvertToDTO.categoryFieldsDTOList(
                this.categoryFieldsRepository.findByCategoryAndChilds(
                        categoryId, childId));
    }

    // Category Fields Service
    @Transactional
    public void reorderField(DisplayOrderDTO dto) {
        if (dto.getOldOrder() > dto.getNewOrder()) {
            categoryFieldsRepository.shiftDown(dto.getCategoryId(), dto.getChildId(), dto.getNewOrder(),
                    dto.getOldOrder());
            // categoryFieldsRepository.shiftDown(dto.getFieldId(), dto.getNewOrder(),
            // dto.getOldOrder());
        } else if (dto.getOldOrder() < dto.getNewOrder()) {
            categoryFieldsRepository.shiftUp(dto.getCategoryId(), dto.getChildId(), dto.getOldOrder(),
                    dto.getNewOrder());
            // categoryFieldsRepository.shiftUp(dto.getFieldId(), dto.getNewOrder(),
            // dto.getOldOrder());
        }

        // categoryFieldsRepository.updateOrder(dto.getCategoryId(),
        // dto.getAttributeKey(), dto.getNewOrder());
        categoryFieldsRepository.updateOrder(dto.getFieldId(), dto.getNewOrder());
    }

    @Transactional
    public void allReorder(List<Integer> fieldIds, List<Integer> newOrders) {
        if (fieldIds.size() != newOrders.size()) {
            throw new IllegalArgumentException("fieldIds와 newOrders의 크기가 다릅니다.");
        }

        for (int i = 0; i < fieldIds.size(); i++) {
            this.categoryFieldsRepository.updateOrders(fieldIds.get(i), newOrders.get(i));
        }
    }
}
