package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Alert} entities.
 * This controller provides endpoints for various CRUD operations related to alerts,
 * making it possible to retrieve, create, update, and delete alerts through HTTP requests.
 * <p>
 * The controller is mapped to the base URL '/api/alerts', from which all its endpoints are derived.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #getAllAlerts()} - Retrieves a list of all alerts.</li>
 *     <li>{@link #getAlertById(Integer)} - Retrieves a specific alert by its ID.</li>
 *     <li>{@link #createAlert(Alert)} - Creates a new alert.</li>
 *     <li>{@link #updateAlert(Integer, Alert)} - Updates an existing alert identified by ID.</li>
 *     <li>{@link #deleteAlert(Integer)} - Deletes an alert by its ID.</li>
 * </ul>
 *
 * @see AlertService
 */
@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Endpoint to retrieve all alerts.
     *
     * @return a list of all {@link Alert} entities.
     */
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.findAllAlerts();
    }

    /**
     * Endpoint to retrieve a single alert by its ID.
     *
     * @param id the ID of the alert to retrieve.
     * @return a {@link ResponseEntity} containing the found alert, or a Not Found response if no alert is found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Integer id) {
        return alertService.findAlertById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new alert.
     *
     * @param alert the {@link Alert} to create.
     * @return the created alert.
     */
    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }

    /**
     * Endpoint to update an existing alert.
     *
     * @param id the ID of the alert to update.
     * @param alert the {@link Alert} details to update.
     * @return a {@link ResponseEntity} with the updated alert, or a Not Found response if the alert could not be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable Integer id, @RequestBody Alert alert) {
        alert.setId(id); // Ensure the ID is set to the one provided in the path
        Alert updatedAlert = alertService.updateAlert(alert);
        if (updatedAlert != null) {
            return ResponseEntity.ok(updatedAlert);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Endpoint to delete an alert by its ID.
     *
     * @param id the ID of the alert to delete.
     * @return a {@link ResponseEntity} indicating success (200 OK) or failure (404 Not Found) based on whether the alert was found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Integer id) {
        if (alertService.findAlertById(id).isPresent()) {
            alertService.deleteAlert(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
