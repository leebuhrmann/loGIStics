package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link Alert} entities, extending the {@link JpaRepository}.
 * This interface is used to facilitate database operations on alert entities such as
 * creating, reading, updating, and deleting alerts.
 * <p>
 * In addition to the standard CRUD operations provided by JpaRepository, this interface
 * includes custom behavior to check the existence of an alert based on its NWS ID.
 * <p>
 * Usage:
 * This repository should be used whenever interactions with the Alert table are required.
 * It provides a method to quickly verify the existence of an alert by its NWS ID,
 * which is useful in operations where duplication checks are necessary.
 *
 * @see Alert
 */
public interface AlertRepository extends JpaRepository<Alert, Integer> {

    /**
     * Checks if an alert with the specified NWS ID exists in the database.
     *
     * @param nwsID the NWS ID of the alert to check
     * @return true if an alert with the given NWS ID exists, false otherwise
     */
    boolean existsByNwsID(String nwsID);
}
