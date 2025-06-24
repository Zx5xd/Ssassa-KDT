package web.ssa.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import web.ssa.dto.categories.CategoriesDTO;
import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.CategoriesChild;
import web.ssa.repository.categories.CategoryChildRepository;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;

@Component
public class CategoriesCache {
    private final CategoryRepository categoryRepository;
    private final CategoryChildRepository categoryChildRepository;

    @Getter
    private List<CategoriesDTO> cachedCategories; // 메모리에 캐싱

    @Getter
    private List<CategoriesChild> cachedCategoriesChild;

    public CategoriesCache(CategoryRepository categoryRepository, CategoryChildRepository categoryChildRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryChildRepository = categoryChildRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("카테고리 캐시 초기화 중...");
        this.cachedCategories = CategoriesDTO.convertToDTOList(this.categoryRepository.findAll());
        this.cachedCategoriesChild =
        System.out.println("카테고리 캐시 완료! 항목 수: " + cachedCategories.size());
    }

    public void reload() {
        this.cachedCategories = CategoriesDTO.convertToDTOList(this.categoryRepository.findAll());
    }
}
