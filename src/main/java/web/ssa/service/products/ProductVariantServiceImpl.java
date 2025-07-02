package web.ssa.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductVariant;
import web.ssa.repository.products.ProductVariantRepository;

import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    public List<ProductVariant> getVariantByMasterId(int masterId) {
        return this.productVariantRepository.findByMasterId_Id(masterId);
    }

    @Override
    public ProductVariant getVariantById(int id) {
        return this.productVariantRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductVariant> getVariantsForProduct(int productId) {
        return this.productVariantRepository.findByMasterId_Id(productId);
    }
}
