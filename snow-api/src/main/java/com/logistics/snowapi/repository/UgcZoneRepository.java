package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.UgcZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UgcZoneRepository extends JpaRepository<UgcZone, String> {
    boolean existsByUgcCode(String ugcCode);

    Optional<UgcZone> findByUgcCode(String ugcCode);

    void deleteByUgcCode(String ugcCode);
}
