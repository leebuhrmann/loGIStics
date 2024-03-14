package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ugc_alert")
public class UgcAlert {
    @EmbeddedId
    private UgcAlertId id;

    @MapsId("ugcCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ugc_code", nullable = false)
    private UgcZone ugcCode;

    @MapsId("alertId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_id", nullable = false)
    private Alert alert;

}