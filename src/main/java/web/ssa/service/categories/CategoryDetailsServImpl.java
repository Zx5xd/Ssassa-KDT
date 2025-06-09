package web.ssa.service.categories;

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
    public List<DetailCategories> getCategoryDetails(int id) {
        return this.detailCategoryRepository.findByCategoryId(id);
    }

    @Override
    public List<DetailCategories> getCategoryDetails(int id, String name) {
        return this.detailCategoryRepository.findByCategoryIdAndCategoryName(id, name);
    }
}
