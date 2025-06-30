package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.ssa.cache.ProductImgCache;
import web.ssa.entity.products.ProductImg;
import web.ssa.entity.products.ProductMaster;
import web.ssa.entity.products.ProductVariant;
import web.ssa.repository.products.ProductImgRepository;
import web.ssa.repository.products.ProductRepository;
import web.ssa.repository.products.ProductVariantRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;
    private ProductImgRepository imgRepository;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ProductImgCache productImgCache;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductMaster> getAllProducts() {
        return this.repository.findAll();
    }

    @Override
    public ProductMaster getProductById(int id) {
        List<ProductMaster> products = this.repository.findById(id);
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public List<ProductMaster> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public List<ProductMaster> findById(int id) {
        return this.repository.findById(id);
    }

    @Override
    public void delete(int id) {
        this.repository.deleteById(id);
    }

    public Page<ProductMaster> getPagedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public Page<ProductMaster> getPagedProductsByCategory(int categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public int getProductPrice(int productId) {
        ProductMaster product = getProductById(productId);
        if (product == null) {
            return 0;
        }

        int price = product.getPrice();

        if (price == 0 && product.getDefaultVariantId() != null) {
            ProductVariant variant = this.productVariantService.getVariantById(product.getDefaultVariantId());
            if (variant != null) {
                price = variant.getPrice();
            }
        }
        return price;
    }

    @Override
    public String getProductSimpleImg(ProductMaster product) {
        return productImgCache.getImageUrl(product.getSimpleImg());
    }
}
