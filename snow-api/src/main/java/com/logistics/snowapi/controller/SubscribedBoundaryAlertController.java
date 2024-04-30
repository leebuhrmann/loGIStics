package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.service.SubscribedBoundaryAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class SubscribedBoundaryAlertController {

    private final SubscribedBoundaryAlertService subscribedBoundaryAlertService;

    @Autowired
    public SubscribedBoundaryAlertController(SubscribedBoundaryAlertService subscribedBoundaryAlertService) {
        this.subscribedBoundaryAlertService = subscribedBoundaryAlertService;
    }

    @GetMapping("/subscribed-boundaries")
    public ResponseEntity<List<Alert>> getAlertsForSubscribedBoundaries() {
        List<Alert> alerts = subscribedBoundaryAlertService.getAlertsForSubscribedBoundaries();
        return ResponseEntity.ok(alerts);
    }
}
