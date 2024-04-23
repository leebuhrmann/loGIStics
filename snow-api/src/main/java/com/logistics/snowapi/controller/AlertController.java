package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.findAllAlerts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Integer id) {
        return alertService.findAlertById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/subscribed-boundaries")
    public ResponseEntity<List<Alert>> getAlertsForSubscribedBoundaries() {
        List<Alert> alerts = alertService.getAlertsForSubscribedBoundaries();
        if (alerts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alerts);
    }

    @PostMapping
    public Alert createAlert(@RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlert(@PathVariable Integer id, @RequestBody Alert alert) {
        alert.setId(id); // Ensure the ID is set to the one provided in the path
        Alert updatedAlert = alertService.updateAlert(alert);
        if (updatedAlert != null) {
            return ResponseEntity.ok(updatedAlert);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Integer id) {
        if (alertService.findAlertById(id).isPresent()) {
            alertService.deleteAlert(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
