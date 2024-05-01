package com.logistics.snowapi.dto;

import com.logistics.snowapi.model.Alert;

public class AlertBoundaryDTO {
    private Alert alert;
    private Integer boundaryId;
    private String boundaryName;

    // Constructor
    public AlertBoundaryDTO(Alert alert, Integer boundaryId, String boundaryName) {
        this.alert = alert;
        this.boundaryId = boundaryId;
        this.boundaryName = boundaryName;
    }

    // Getters and Setters
    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Integer getBoundaryId() {
        return boundaryId;
    }

    public void setBoundaryId(Integer boundaryId) {
        this.boundaryId = boundaryId;
    }

    public String getBoundaryName() {
        return boundaryName;
    }

    public void setBoundaryName(String boundaryName) {
        this.boundaryName = boundaryName;
    }
}
