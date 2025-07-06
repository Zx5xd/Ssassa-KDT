package web.ssa.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import web.ssa.dto.categories.CategoriesDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.enumf.CategoryType;
import web.ssa.repository.categories.CategoryChildRepository;
import web.ssa.repository.categories.CategoryRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CategoriesCache {
    private final CategoryRepository categoryRepository;
    private final CategoryChildRepository categoryChildRepository;

    @Getter
    private List<Categories> cachedCategories; // 메모리에 캐싱

    @Getter
    private List<CategoriesChild> cachedWasherChilds;
    @Getter
    private List<CategoriesChild> cachedPeriperhalChilds;
    @Getter
    private List<CategoriesChild> cachedPCComponentChilds;

    public CategoriesCache(CategoryRepository categoryRepository, CategoryChildRepository categoryChildRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryChildRepository = categoryChildRepository;
    }

    @PostConstruct
    public void init() {
        // System.out.println("카테고리 캐시 초기화 중...");
        this.cachedCategories = this.categoryRepository.findAll();
        // System.out.println("카테고리 캐시 완료! 항목 수: " + cachedCategories.size());

        this.cachedWasherChilds = this.categoryChildRepository
                .findByCategoryChildId(categoriesType(CategoryType.WASHER_DRYER_SET));
        // System.out.println("세탁_건조 캐시 완료! 항목 수 : "+cachedWasherChilds.size());

        this.cachedPeriperhalChilds = this.categoryChildRepository
                .findByCategoryChildId(categoriesType(CategoryType.PC_PERIPHERAL));
        // System.out.println("주변기기 캐시 완료! 항목 수 : "+cachedPeriperhalChilds.size());

        this.cachedPCComponentChilds = this.categoryChildRepository
                .findByCategoryChildId(categoriesType(CategoryType.PC_COMPONENT));
        // System.out.println("부품 캐시 완료! 항목 수 : "+cachedPCComponentChilds.size());

    }

    public void reload() {
        this.cachedCategories = this.categoryRepository.findAll();
    }

    private Categories categoriesType(CategoryType type) {
        return this.categoryRepository.findByCode(
                type).orElse(null);
    }

    // 특정 카테고리의 세부 카테고리 조회
    public List<CategoriesChild> getCategoryChildren(int categoryId) {

        List<CategoriesChild> result;
        switch (categoryId) {
            case 5: // WASHER_DRYER_SET
                result = cachedWasherChilds;
                break;
            case 8: // PC_PERIPHERAL
                result = cachedPeriperhalChilds;
                break;
            case 9: // PC_COMPONENT
                result = cachedPCComponentChilds;
                break;
            default:
                result = List.of(); // 빈 리스트 반환
                break;
        }

        if (result != null) {
            result.forEach(child -> System.out.println("세부 카테고리: " + child.getId() + " - " + child.getName()));
        }

        return result != null ? result : List.of();
    }

}
