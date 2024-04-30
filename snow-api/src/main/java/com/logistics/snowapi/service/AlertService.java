package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.model.AlertDTO;
import com.logistics.snowapi.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    @Autowired
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<AlertDTO> findAllAlerts() {
        // Convert each Alert to an AlertDTO
        return alertRepository.findAll().stream()
                .map(alert -> new AlertDTO(alert.getId(), alert.getEvent(), alert.getOnset(), alert.getExpires(), alert.getHeadline(), alert.getDescription()))
                .collect(Collectors.toList());
    }

    public Optional<AlertDTO> findAlertById(Integer id) {
        // Find the alert and convert it to DTO if present
        return alertRepository.findById(id)
                .map(alert -> new AlertDTO(alert.getId(), alert.getEvent(), alert.getOnset(), alert.getExpires(), alert.getHeadline(), alert.getDescription()));
    }

    public AlertDTO createAlert(Alert alert) {
        // Ensure the alert doesn't exist based on a unique field (e.g., nwsID)
        if (alert.getNwsID() != null && !alertRepository.existsByNwsID(alert.getNwsID())) {
            Alert savedAlert = alertRepository.save(alert);
            return new AlertDTO(savedAlert.getId(), savedAlert.getEvent(), savedAlert.getOnset(), savedAlert.getExpires(), savedAlert.getHeadline(), savedAlert.getDescription());
        }
        return null;
    }

    public AlertDTO updateAlert(Alert alert) {
        // Update the alert if it exists
        if (alert.getId() != null && alertRepository.existsById(alert.getId())) {
            Alert updatedAlert = alertRepository.save(alert);
            return new AlertDTO(updatedAlert.getId(), updatedAlert.getEvent(), updatedAlert.getOnset(), updatedAlert.getExpires(), updatedAlert.getHeadline(), updatedAlert.getDescription());
        }
        return null;
    }

    public void deleteAlert(Integer id) {
        alertRepository.deleteById(id);
    }
}
