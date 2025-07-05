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

        // 테스트용 로그 추가
        System.out.println("=== 카테고리 캐시 초기화 완료 ===");
        System.out.println("전체 카테고리 수: " + (cachedCategories != null ? cachedCategories.size() : 0));
        System.out.println("세탁/건조기 세부 카테고리 수: " + (cachedWasherChilds != null ? cachedWasherChilds.size() : 0));
        System.out
                .println("PC 주변기기 세부 카테고리 수: " + (cachedPeriperhalChilds != null ? cachedPeriperhalChilds.size() : 0));
        System.out
                .println("PC 부품 세부 카테고리 수: " + (cachedPCComponentChilds != null ? cachedPCComponentChilds.size() : 0));

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
        System.out.println("getCategoryChildren 호출됨 - categoryId: " + categoryId);

        List<CategoriesChild> result;
        switch (categoryId) {
            case 5: // WASHER_DRYER_SET
                result = cachedWasherChilds;
                System.out.println("세탁/건조기 세부 카테고리 수: " + (result != null ? result.size() : 0));
                break;
            case 8: // PC_PERIPHERAL
                result = cachedPeriperhalChilds;
                System.out.println("PC 주변기기 세부 카테고리 수: " + (result != null ? result.size() : 0));
                break;
            case 9: // PC_COMPONENT
                result = cachedPCComponentChilds;
                System.out.println("PC 부품 세부 카테고리 수: " + (result != null ? result.size() : 0));
                break;
            default:
                result = List.of(); // 빈 리스트 반환
                System.out.println("해당하는 카테고리가 없음");
                break;
        }

        if (result != null) {
            result.forEach(child -> System.out.println("세부 카테고리: " + child.getId() + " - " + child.getName()));
        }

        return result != null ? result : List.of();
    }

}
