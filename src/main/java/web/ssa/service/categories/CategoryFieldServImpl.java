package web.ssa.service.categories;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ssa.cache.CategoriesCache;
import web.ssa.dto.categories.CategoryFieldsDTO;
import web.ssa.dto.categories.DisplayOrderDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.entity.categories.CategoryFields;
import web.ssa.mapper.ConvertToDTO;
import web.ssa.repository.categories.CategoryFieldsRepository;
import web.ssa.util.DTOUtil;

import java.util.List;
import java.util.Map;

@Service
public class CategoryFieldServImpl implements CategoryFieldServ {

    @Autowired
    private CategoryFieldsRepository categoryFieldsRepository;
    @Autowired
    private CategoriesCache categoriesCache;
    @Autowired
    private CategoryChildServImpl categoryChildServ;

    private DTOUtil dtoUtil = new DTOUtil();

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
        } else if (dto.getOldOrder() < dto.getNewOrder()) {
            categoryFieldsRepository.shiftUp(dto.getCategoryId(), dto.getChildId(), dto.getOldOrder(),
                    dto.getNewOrder());
        }
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

    @Transactional
    public void reValueList(CategoryFieldsDTO fDto) {
        if (fDto.getValueList().equals("{}")) {

        }
    }

    @Override
    public CategoryFieldsDTO getCategoryFieldById(int fieldId) {
        CategoryFields field = categoryFieldsRepository.findById(fieldId).orElse(null);
        return field != null ? ConvertToDTO.categoryFieldsDTO(field) : null;
    }

    @Override
    @Transactional
    public void updateValueList(int fieldId, String newValueList) {
        CategoryFields field = categoryFieldsRepository.findById(fieldId)
                .orElseThrow(() -> new IllegalArgumentException("필드를 찾을 수 없습니다: " + fieldId));
        field.setValueList(newValueList);
        categoryFieldsRepository.save(field);
    }

    public List<CategoryFieldsDTO> getCatFieldsByCategoryId(int categoryId) {
        Categories category = new Categories();
        List<CategoriesChild> child = null;
        if (categoryId == 5 || categoryId == 8 || categoryId == 9) {
            category = this.categoriesCache.getCachedCategories().stream()
                    .filter(cat -> cat.getId() == categoryId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));

            child = this.categoryChildServ.getCategoryChild(category);
        }
        System.out.println("id : " + categoryId);
        return switch (categoryId) {
            case 5, 8, 9 ->
                this.getCategoryFieldsByChildId(categoryId,
                        child);
            default -> this.getCategoryFieldsByCategoryId(categoryId);
        };
    }

    public Map<String, List<String>> setFilter(int categoryId) {
        // 1. 해당 카테고리의 Fields를 불러오기
        List<CategoryFieldsDTO> fields = getCatFieldsByCategoryId(categoryId);

        // if (fields == null || fields.isEmpty()) {
        // return fields;
        // }

        // 2. DisplayOrder 순으로 나열
        fields = dtoUtil.sortCategoryFieldsByDisplayOrder(fields);
        System.out.println("[ 필터 테스트 / DisplayOrder 순으로 나열 ] fields : " + fields);

        // 3. valueList를 weight 순으로 정렬하고 value 값 추출하여 unit과 결합
        Map<String, List<String>> valueListMap = dtoUtil.processCategoryFieldsForFilterAsSimpleMap(fields);

        System.out.println("[ 필터 테스트 / valueListMap ] valueListMap : " + valueListMap);
        valueListMap.forEach((key, value) -> {
            System.out.println("key : " + key);
            value.forEach(v -> {
                System.out.println("v : " + v);
            });
        });

        return valueListMap;
    }

    @Override
    public List<CategoryFieldsDTO> getCategoryFieldsByChildId(int categoryId, int childId) {
        // TODO Auto-generated method stub
        return ConvertToDTO.categoryFieldsDTOList(
                this.categoryFieldsRepository.findByCategoryFieldId_IdAndCategoryChildId_Id(categoryId, childId));
    }
}
