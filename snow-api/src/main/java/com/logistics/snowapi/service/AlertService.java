package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> findAllAlerts() {
        return alertRepository.findAll();
    }

    public Optional<Alert> findAlertById(Integer id) {
        return alertRepository.findById(id);
    }

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public Alert updateAlert(Alert alert) {
        // Make sure the alert exists before updating
        if (alert.getId() != null && alertRepository.existsById(alert.getId())) {
            return alertRepository.save(alert);
        }
        // Handle the case where the alert doesn't exist (could throw an exception or return null)
        return null;
    }

    public void deleteAlert(Integer id) {
        alertRepository.deleteById(id);
    }
}
