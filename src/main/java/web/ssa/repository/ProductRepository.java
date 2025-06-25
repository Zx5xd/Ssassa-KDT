package web.ssa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.ssa.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}