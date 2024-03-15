package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.Boundary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoundaryRepository extends JpaRepository<Boundary, Integer> {
    // TBD
    // Additional custom query methods can be defined here
}