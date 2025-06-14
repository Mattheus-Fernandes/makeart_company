package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    boolean existsByRg(String rg);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
