package com.makeart.makeart_server.infrastructure.repository;


import com.makeart.makeart_server.infrastructure.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCode(String code);
    boolean existsByDescription(String description);
    Optional<Product> findByCode(String code);
    List<Product> findAllByBrandCode(String code);
    List<Product> findAllByCategoryCode(String code);
    List<Product> findAllBySubcategoryCode(String code);
    List<Product> findByDescriptionStartingWithIgnoreCase(String description);

}
