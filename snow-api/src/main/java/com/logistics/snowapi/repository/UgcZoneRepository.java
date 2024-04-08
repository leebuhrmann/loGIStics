package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.UgcZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UgcZoneRepository extends JpaRepository<UgcZone, String> {
}
