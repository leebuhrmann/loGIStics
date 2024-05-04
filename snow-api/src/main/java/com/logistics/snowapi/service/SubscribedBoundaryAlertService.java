package com.logistics.snowapi.service;

import com.logistics.snowapi.dto.AlertBoundaryDTO;
import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.SubscribedBoundaryAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubscribedBoundaryAlertService {

    private final SubscribedBoundaryAlertRepository subscribedBoundaryAlertRepository;

    @Autowired
    public SubscribedBoundaryAlertService(SubscribedBoundaryAlertRepository subscribedBoundaryAlertRepository) {
        this.subscribedBoundaryAlertRepository = subscribedBoundaryAlertRepository;
    }

    /**
     * Gets all alerts that fall within subscribed boundaries
     * 
     * @return A list of alerts with lists of boundary ids and boundary names.
     */
    public List<AlertBoundaryDTO> getAlertsWithBoundaries() {
        List<Object[]> results = subscribedBoundaryAlertRepository.findAllAlertsBySubscribedBoundaries();
        Map<Alert, AlertBoundaryDTO> alertBoundaryMap = results.stream()
                .collect(Collectors.toMap(
                        result -> (Alert) result[0],
                        result -> new AlertBoundaryDTO((Alert) result[0], (Integer) result[1], (String) result[2]),
                        (existing, additional) -> {
                            existing.addBoundary(additional.getBoundaryIds().get(0),
                                    additional.getBoundaryNames().get(0));
                            return existing;
                        }));

        return new ArrayList<>(alertBoundaryMap.values());
    }
}
