package com.makeart.makeart_server.infrastructure.repository;


import com.makeart.makeart_server.infrastructure.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    boolean existsByDescription(String description);
    Optional<Product> findByCode(String code);
    List<Product> findByCodeContainsIgnoreCase(String code);
    List<Product> findByBrand_CodeContainsIgnoreCase(String code);
    List<Product> findByCategory_CodeContainsIgnoreCase(String code);
    List<Product> findBySubcategory_CodeContainsIgnoreCase(String code);
    List<Product> findByDescriptionContainsIgnoreCase(String description);

}
