package com.logistics.snowapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Embeddable class representing the composite key for the {@link UgcAlert} entity.
 * This class defines the fields that make up the primary key for the UgcAlert table, including
 * a UGC code and an alert ID. It implements {@link java.io.Serializable} to ensure that instances
 * of this class can be serialized by JPA and other Java technologies.
 * <p>
 * The fields:
 * <ul>
 *     <li>{@code ugcCode} - Represents the UGC (Universal Geographic Code), a unique identifier for a geographic zone.</li>
 *     <li>{@code alertId} - Represents the identifier for an {@link Alert}, linking the alert to a specific UGC zone.</li>
 * </ul>
 * <p>
 * This class includes custom implementations of {@code equals} and {@code hashCode} methods, which are essential for
 * correctly handling composite keys in JPA. These implementations leverage Hibernate's {@link Hibernate#getClass(Object)}
 * method to ensure that proxy objects are correctly compared, preventing issues with lazy loading.
 *
 * @see UgcAlert
 * @see Hibernate
 */
@Getter
@Setter
@Embeddable
public class UgcAlertId implements java.io.Serializable {
    private static final long serialVersionUID = -6307879508368276376L;
    @Column(name = "ugc_code", nullable = false, length = 6)
    private String ugcCode;

    @Column(name = "alert_id", nullable = false)
    private Integer alertId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UgcAlertId entity = (UgcAlertId) o;
        return Objects.equals(this.ugcCode, entity.ugcCode) &&
                Objects.equals(this.alertId, entity.alertId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ugcCode, alertId);
    }

}