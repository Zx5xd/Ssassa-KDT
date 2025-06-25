package web.ssa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.ssa.entity.Product;
import web.ssa.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}