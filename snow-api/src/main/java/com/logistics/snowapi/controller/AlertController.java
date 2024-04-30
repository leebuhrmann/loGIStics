package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.AlertDTO;
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
    public List<AlertDTO> getAllAlerts() {
        return alertService.findAllAlerts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertDTO> getAlertById(@PathVariable Integer id) {
        return alertService.findAlertById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlertDTO> createAlert(@RequestBody AlertDTO alertDTO) {
        AlertDTO createdAlert = alertService.createAlertDTO(alertDTO);
        if (createdAlert != null) {
            return ResponseEntity.ok(createdAlert);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertDTO> updateAlert(@PathVariable Integer id, @RequestBody AlertDTO alertDTO) {
        alertDTO.setId(id); // Set the ID to ensure the correct alert is updated
        AlertDTO updatedAlert = alertService.updateAlertDTO(alertDTO);
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
