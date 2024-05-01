package com.logistics.snowapi.service;

import com.logistics.snowapi.dto.AlertBoundaryDTO;
import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.SubscribedBoundaryAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscribedBoundaryAlertService {

    private final SubscribedBoundaryAlertRepository subscribedBoundaryAlertRepository;

    @Autowired
    public SubscribedBoundaryAlertService(SubscribedBoundaryAlertRepository subscribedBoundaryAlertRepository) {
        this.subscribedBoundaryAlertRepository = subscribedBoundaryAlertRepository;
    }

    public List<AlertBoundaryDTO> getAlertsWithBoundaries() {
        List<Object[]> results = subscribedBoundaryAlertRepository.findAllAlertsBySubscribedBoundaries();
        return results.stream()
                .map(result -> new AlertBoundaryDTO((Alert) result[0], (Integer) result[1], (String) result[2]))
                .collect(Collectors.toList());
    }
}
