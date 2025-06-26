package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.dto.products.SimpleProductDTO;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.repository.products.ProductImgRepository;
import web.ssa.repository.products.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductImgRepository productImgRepository;

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
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<ProductMaster> findByCategoryId(int categoryId, Pageable pageable) {
        return this.repository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<SimpleProductDTO> findBySimpleCategoryId(int categoryId, Pageable pageable) {
        Page<ProductMaster> page = this.repository.findByCategoryId(categoryId, pageable);
        return page.map(SimpleProductDTO::from);
    }

    @Override
    public Page<ProductMaster> findByCategoryChildId(int categoryChildId, Pageable pageable) {
        return this.repository.findByCategoryChildId(categoryChildId, pageable);
    }

    @Override
    public ProductImg findByImgId(int productImgId) {
        return this.productImgRepository.findById(productImgId);
    }
}
