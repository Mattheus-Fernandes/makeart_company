package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByCode(String code);
    boolean existsByDescription(String description);
    Optional<Category> findByCode(String code);
    List<Category> findByCodeContainsIgnoreCase(String code);
    List<Category> findByDescriptionContainsIgnoreCase(String description);
}
