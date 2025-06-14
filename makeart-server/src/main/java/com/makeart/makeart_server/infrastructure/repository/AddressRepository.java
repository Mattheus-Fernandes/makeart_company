package com.makeart.makeart_server.infrastructure.repository;

import com.makeart.makeart_server.infrastructure.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
