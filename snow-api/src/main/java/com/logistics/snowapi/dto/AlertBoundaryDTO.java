package com.logistics.snowapi.dto;

import java.util.ArrayList;
import java.util.List;

import com.logistics.snowapi.model.Alert;

public class AlertBoundaryDTO {
    private Alert alert;
    private List<Integer> boundaryIds;
    private List<String> boundaryNames;

    // Constructor
    public AlertBoundaryDTO(Alert alert, Integer boundaryId, String boundaryName) {
        this.alert = alert;
        this.boundaryIds = new ArrayList<>();
        this.boundaryNames = new ArrayList<>();
        this.boundaryIds.add(boundaryId);
        this.boundaryNames.add(boundaryName);
    }

    // Getters and Setters
    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public List<Integer> getBoundaryIds() {
        return boundaryIds;
    }

    public List<String> getBoundaryNames() {
        return boundaryNames;
    }

    public void addBoundary(Integer boundaryId, String boundaryName) {
        this.boundaryIds.add(boundaryId);
        this.boundaryNames.add(boundaryName);
    }
}
