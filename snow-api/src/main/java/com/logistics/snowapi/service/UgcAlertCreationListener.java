package com.logistics.snowapi.service;

import com.logistics.snowapi.events.AlertCreatedEvent;
import com.logistics.snowapi.events.UgcZoneCreatedEvent;
import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.model.UgcAlert;
import com.logistics.snowapi.model.UgcAlertId;
import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.UgcAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UgcAlertCreationListener {

    @Autowired
    private UgcAlertRepository ugcAlertRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private UgcZoneRepository ugcZoneRepository;

    @EventListener
    public void handleAlertCreated(AlertCreatedEvent event) {
        // Handle event logic
    }

    @EventListener
    public void handleUgcZoneCreated(UgcZoneCreatedEvent event) {
        // Handle event logic
    }

    private void createUgcAlert(Alert alert, UgcZone zone) {
        // Implementation details
    }
}
