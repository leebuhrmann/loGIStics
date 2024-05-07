package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that encapsulates the business logic for managing alerts.
 * This class provides methods to find, create, update, and delete {@link Alert} entities,
 * using the {@link AlertRepository} for database interactions.
 * <p>
 * Methods in this class handle standard CRUD operations and ensure that the business rules
 * for processing alerts, such as uniqueness checks and existence validations, are enforced.
 * <p>
 * Usage:
 * This service should be used whenever {@link Alert} entities need to be manipulated or queried.
 * The methods provided ensure that alerts are handled consistently and in accordance with
 * the defined business rules.
 *
 * @see Alert
 * @see AlertRepository
 */
@Service
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * Retrieves all alerts stored in the database.
     *
     * @return a list of {@link Alert} entities
     */
    public List<Alert> findAllAlerts() {
        return alertRepository.findAll();
    }

    /**
     * Retrieves an alert by its ID.
     *
     * @param id the ID of the alert to retrieve
     * @return an {@link Optional} containing the found alert or empty if no alert is found
     */
    public Optional<Alert> findAlertById(Integer id) {
        return alertRepository.findById(id);
    }

    /**
     * Creates a new alert in the database, ensuring it does not already exist based on its NWS ID.
     *
     * @param alert the {@link Alert} entity to create
     * @return the created alert if successful, null otherwise
     */
    public Alert createAlert(Alert alert) {
        // Make sure the alert doesn't exist before creating
        if (alert.getNwsID() != null && !alertRepository.existsByNwsID(alert.getNwsID())) {
            return alertRepository.save(alert);
        }
        return null;
    }

    /**
     * Updates an existing alert in the database.
     *
     * @param alert the {@link Alert} entity to update
     * @return the updated alert if successful, null otherwise
     */
    public Alert updateAlert(Alert alert) {
        // Make sure the alert exists before updating
        if (alert.getId() != null && alertRepository.existsById(alert.getId())) {
            return alertRepository.save(alert);
        }
        // Handle the case where the alert doesn't exist (could throw an exception or return null)
        return null;
    }

    /**
     * Deletes an alert from the database by its ID.
     *
     * @param id the ID of the alert to delete
     */
    public void deleteAlert(Integer id) {
        alertRepository.deleteById(id);
    }
}
