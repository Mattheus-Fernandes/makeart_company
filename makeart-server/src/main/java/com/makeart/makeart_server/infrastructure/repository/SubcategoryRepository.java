package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    boolean existsByCode(String code);
    boolean existsByDescription(String description);
    Optional<Subcategory> findByCode(String code);
    List<Subcategory> findByCodeContainsIgnoreCase(String code);
    List<Subcategory> findByDescriptionContainsIgnoreCase(String description);
    List<Subcategory> findByCategory_CodeContainsIgnoreCase(String code);
    List<Subcategory> findByCodeContainsIgnoreCaseAndCategory_CodeContainsIgnoreCase(String code, String categoryCode);
    @Transactional
    void deleteSubcategoryByCode(String code);
}
