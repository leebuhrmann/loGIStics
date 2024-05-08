package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.Boundary;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for {@link Boundary} entities, extending {@link JpaRepository}.
 * This interface facilitates database operations on boundary entities such as
 * creating, reading, updating, and deleting boundaries.
 * <p>
 * Usage:
 * This repository is intended for direct interaction with the Boundary table in the database.
 * It serves as the primary access point for data retrieval and manipulation operations on boundary
 * entities, supporting a variety of application functionalities that deal with geographic data.
 *</p>
 *
 * @see Boundary
 */
public interface BoundaryRepository extends JpaRepository<Boundary, Integer> { }