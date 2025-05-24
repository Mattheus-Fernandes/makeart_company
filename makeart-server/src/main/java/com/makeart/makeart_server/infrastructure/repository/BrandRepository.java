package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    boolean existsByCode(String code);
    boolean existsByDescription(String description);
    Optional<Brand> findByCode(String code);
    List<Brand> findByCodeContainsIgnoreCase(String code);
    List<Brand> findByDescriptionContainsIgnoreCase(String description);
}
