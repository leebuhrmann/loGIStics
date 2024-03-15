package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.SnowUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnowUserRepository extends JpaRepository<SnowUser, Integer> {
    // TBD
    // Custom query methods can be added here
}
