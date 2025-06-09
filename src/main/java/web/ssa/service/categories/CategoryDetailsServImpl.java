package web.ssa.service.categories;

import web.ssa.entity.categories.Categories;
import web.ssa.entity.categories.DetailCategories;
import web.ssa.repository.categories.CategoryRepository;
import web.ssa.repository.categories.DetailCategoryRepository;

import java.util.List;

public class CategoryDetailsServImpl implements CategoryDetailsServ {

    private DetailCategoryRepository detailCategoryRepository;

    @Override
    public List<DetailCategories> getCategoryDetails() {
        return this.detailCategoryRepository.findAll();
    }

    @Override
    public List<DetailCategories> getCategoryDetails(Categories id) {
        return this.detailCategoryRepository.findByCategoryDetailId(id);
    }

    @Override
    public List<DetailCategories> getCategoryDetails(Categories id, String name) {
        return this.detailCategoryRepository.findByCategoryDetailIdAndCode(id, name);
    }
}
