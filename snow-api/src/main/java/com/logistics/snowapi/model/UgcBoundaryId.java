package com.logistics.snowapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Embeddable class representing the composite key for the {@link UgcBoundary} entity.
 * This class defines the fields that make up the primary key for the UgcBoundary table, including
 * a UGC (Universal Geographic Code) and a boundary ID. It implements {@link java.io.Serializable} to ensure that
 * instances of this class can be serialized by JPA and other Java technologies.
 * <p>
 * Fields:
 * <ul>
 *     <li>{@code ugcCode} - The UGC code, a unique identifier for a geographic zone, serving as part of the composite key.</li>
 *     <li>{@code boundaryId} - The identifier for a {@link Boundary}, linking the boundary to a specific UGC zone as part of the composite key.</li>
 * </ul>
 * <p>
 * This class includes custom implementations of {@code equals} and {@code hashCode} methods, which are essential for
 * correctly handling composite keys in JPA. These implementations ensure that proxy objects are properly accounted for,
 * thus preventing issues related to lazy loading and entity identity.
 *
 * @see UgcBoundary
 */
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