package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.UgcZone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link UgcZone} entities, extending {@link JpaRepository}.
 * This interface facilitates database operations on UGC (Universal Geographic Code) Zone entities,
 * providing standard CRUD operations as well as several custom query methods tailored to specific
 * requirements for managing geographic zones within the application.
 * <p>
 * The repository provides functionality to check the existence of a UGC Zone by its code, retrieve
 * a UGC Zone by its code, and delete a UGC Zone by its code. These methods are crucial for operations
 * that require handling geographic zones uniquely identified by UGC codes, ensuring that data
 * integrity and uniqueness are maintained.
 * <p>
 * Usage:
 * This repository is integral for operations that involve geographic zone data, particularly when
 * identifying, updating, or removing zones based on their unique UGC codes. It supports functionalities
 * across the application that deal with geographic information management, making it essential for
 * features that require precise and efficient querying and manipulation of geographic data.
 *
 * @see UgcZone
 */
public interface UgcZoneRepository extends JpaRepository<UgcZone, String> {

    /**
     * Checks if a UGC Zone with the specified UGC code exists in the database.
     *
     * @param ugcCode the UGC code of the zone to check
     * @return true if an entity with the given UGC code exists, false otherwise
     */
    boolean existsByUgcCode(String ugcCode);

    /**
     * Retrieves a UGC Zone by its UGC code.
     *
     * @param ugcCode the UGC code of the zone to retrieve
     * @return an {@link Optional} containing the found UGC Zone or empty if no zone is found
     */
    Optional<UgcZone> findByUgcCode(String ugcCode);

    /**
     * Deletes a UGC Zone by its UGC code.
     *
     * @param ugcCode the UGC code of the zone to delete
     */
    void deleteByUgcCode(String ugcCode);
}
