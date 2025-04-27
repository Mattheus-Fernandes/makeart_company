package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
}
