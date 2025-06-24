package web.ssa.service.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductMaster;
import web.ssa.repository.products.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;

    @Override
    public List<ProductMaster> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public List<ProductMaster> findById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public int delete(int id) {
        return this.repository.deleteById(id);
    }

    public Page<ProductMaster> getPagedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
}
