package web.ssa.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.enumf.CategoryType;
import web.ssa.repository.categories.CategoryChildRepository;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;

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
//        System.out.println("카테고리 캐시 초기화 중...");
        this.cachedCategories = this.categoryRepository.findAll();
//        System.out.println("카테고리 캐시 완료! 항목 수: " + cachedCategories.size());

        this.cachedWasherChilds = this.categoryChildRepository.findByCategoryChildId(categoriesType(CategoryType.WASHER_DRYER_SET));
//        System.out.println("세탁_건조 캐시 완료! 항목 수 : "+cachedWasherChilds.size());

        this.cachedPeriperhalChilds =
                this.categoryChildRepository.findByCategoryChildId(categoriesType(CategoryType.PC_PERIPHERAL));
//        System.out.println("주변기기 캐시 완료! 항목 수 : "+cachedPeriperhalChilds.size());

        this.cachedPCComponentChilds =
                this.categoryChildRepository.findByCategoryChildId(categoriesType(CategoryType.PC_COMPONENT));
//        System.out.println("부품 캐시 완료! 항목 수 : "+cachedPCComponentChilds.size());
    }

    public void reload() {
        this.cachedCategories = this.categoryRepository.findAll();
    }

    private Categories categoriesType(CategoryType type) {
        return this.categoryRepository.findByCode(
                type
        ).orElse(null);
    }
}
