package com.logistics.snowapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

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