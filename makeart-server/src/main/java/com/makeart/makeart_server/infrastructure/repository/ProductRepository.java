package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Brand;
import com.makeart.makeart_server.infrastructure.entity.Category;
import com.makeart.makeart_server.infrastructure.entity.Product;
import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    boolean existsByDescription(String description);
    Optional<Brand> findByCodeBrand(String code);
    Optional<Category> findByCodeCategory(String code);
    Optional<Subcategory> findByCodeSubcategory(String code);
}
