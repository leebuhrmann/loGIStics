package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.SubscribedBoundariesAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscribedBoundariesAlertService {

    private final SubscribedBoundariesAlertRepository subscribedBoundariesAlertRepository;

    @Autowired
    public SubscribedBoundariesAlertService(SubscribedBoundariesAlertRepository subscribedBoundariesAlertRepository) {
        this.subscribedBoundariesAlertRepository = subscribedBoundariesAlertRepository;
    }

    public List<Alert> getAlertsForSubscribedBoundaries() {
        return subscribedBoundariesAlertRepository.findAllAlertsBySubscribedBoundaries();
    }
}
