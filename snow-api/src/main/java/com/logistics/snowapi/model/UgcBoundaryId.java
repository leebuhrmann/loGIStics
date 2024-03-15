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
public class UgcBoundaryId implements java.io.Serializable {
    private static final long serialVersionUID = 5085773454720063781L;
    @Column(name = "ugc_code", nullable = false, length = 6)
    private String ugcCode;

    @Column(name = "boundary_id", nullable = false)
    private Integer boundaryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UgcBoundaryId entity = (UgcBoundaryId) o;
        return Objects.equals(this.ugcCode, entity.ugcCode) &&
                Objects.equals(this.boundaryId, entity.boundaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ugcCode, boundaryId);
    }

}