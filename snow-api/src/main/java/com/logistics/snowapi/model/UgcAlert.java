package com.logistics.snowapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "ugc_alert")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ugcCode")
public class UgcAlert {
    @EmbeddedId
    private UgcAlertId id;

    @MapsId("alertId") // Maps alertId part of composite ID
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_id", nullable = false)
    private Alert alert;

    @MapsId("ugcCode") // Maps ugcCode part of composite ID
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ugc_code", nullable = false)
    private UgcZone ugcCode;

    public UgcAlertId getId() {
        return id;
    }

    public void setId(UgcAlertId id) {
        this.id = id;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public UgcZone getUgcCode() {
        return ugcCode;
    }

    public void setUgcCode(UgcZone ugcCode) {
        this.ugcCode = ugcCode;
    }
}
