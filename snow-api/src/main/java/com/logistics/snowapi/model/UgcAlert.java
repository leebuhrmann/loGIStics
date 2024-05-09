package com.logistics.snowapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

/**
 * Entity class for representing an association between an alert and a UGC (Universal Geographic Code) zone.
 * This class is mapped to the 'ugc_alert' table in the database. Each instance of this class links an alert
 * to a specific geographic zone using a composite key.
 * <p>
 * The {@link UgcAlert} entity uses an embedded composite key represented by {@link UgcAlertId}, which includes
 * identifiers for both the alert and the UGC zone. This design facilitates the management of many-to-many
 * relationships between {@link Alert} and {@link UgcZone} entities, ensuring data integrity and enabling efficient
 * querying.
 * <p>
 * Relationships:
 * <ul>
 *     <li>{@code alert} - Many-to-one relationship with the {@link Alert} entity. Each {@code UgcAlert} is linked
 *     to one {@link Alert}, represented by the {@code alert_id} foreign key in the database.</li>
 *     <li>{@code ugcCode} - Many-to-one relationship with the {@link UgcZone} entity. Each {@code UgcAlert} is linked
 *     to one {@link UgcZone}, represented by the {@code ugc_code} foreign key in the database.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are used within the application to track which alerts are associated with which
 * geographic zones, supporting functionalities such as alert dissemination based on geographic relevance.
 *
 * @see UgcAlertId
 * @see Alert
 * @see UgcZone
 */
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
