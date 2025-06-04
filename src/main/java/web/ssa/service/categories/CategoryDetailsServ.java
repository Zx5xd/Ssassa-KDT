package web.ssa.service.categories;

import org.springframework.stereotype.Service;
import web.ssa.entity.categories.DetailCategories;

import java.util.List;

@Service
public interface CategoryDetailsServ {
    List<DetailCategories> getCategoryDetails();
    List<DetailCategories> getCategoryDetails(int id);
    List<DetailCategories> getCategoryDetails(int id, String name);
}
