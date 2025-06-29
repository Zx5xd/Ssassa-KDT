package web.ssa.service.products;

import org.springframework.stereotype.Service;
import web.ssa.entity.products.ProductVariant;

import java.util.List;

@Service
public interface ProductVariantService {
    List<ProductVariant> getVariantByMasterId(int masterId);

    ProductVariant getVariantById(int id);
}
