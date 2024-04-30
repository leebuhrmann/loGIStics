package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.SubscribedBoundaryAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscribedBoundaryAlertService {

    private final SubscribedBoundaryAlertRepository subscribedBoundaryAlertRepository;

    @Autowired
    public SubscribedBoundaryAlertService(SubscribedBoundaryAlertRepository subscribedBoundaryAlertRepository) {
        this.subscribedBoundaryAlertRepository = subscribedBoundaryAlertRepository;
    }

    public List<Alert> getAlertsForSubscribedBoundaries() {
        return subscribedBoundaryAlertRepository.findAllAlertsBySubscribedBoundaries();
    }
}
