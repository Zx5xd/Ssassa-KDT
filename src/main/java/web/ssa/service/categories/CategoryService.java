package web.ssa.service.categories;

import org.springframework.stereotype.Service;
import web.ssa.entity.categories.Categories;

import java.util.List;

@Service
public interface CategoryService {
    List<Categories> getCategories();
    Categories getCategoryById(int id);

    Categories getCategoryByName(String name);
}
