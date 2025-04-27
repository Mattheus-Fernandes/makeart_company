package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
