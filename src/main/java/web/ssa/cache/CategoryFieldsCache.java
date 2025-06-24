package web.ssa.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;
import web.ssa.dto.categories.CategoriesDTO;
import web.ssa.repository.categories.CategoryRepository;

import java.util.List;

@Component
public class CategoryFieldsCache {
    private final CategoryRepository categoryRepository;

    @Getter
    private List<CategoriesDTO> cachedCategories; // 메모리에 캐싱

    public CategoryFieldsCache(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("카테고리 캐시 초기화 중...");
        this.cachedCategories = CategoriesDTO.convertToDTOList(this.categoryRepository.findAll());
        System.out.println("카테고리 캐시 완료! 항목 수: " + cachedCategories.size());
    }

    public void reload() {
        this.cachedCategories = CategoriesDTO.convertToDTOList(this.categoryRepository.findAll());
    }
}
