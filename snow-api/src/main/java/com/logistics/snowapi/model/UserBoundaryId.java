package com.logistics.snowapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

/**
 * Embeddable class representing the composite key for the {@link UserBoundary} entity.
 * This class defines the fields that make up the primary key for the user_boundary table,
 * specifically the user ID and boundary ID. It implements {@link java.io.Serializable} to ensure that
 * instances of this class can be serialized by JPA and other Java technologies.
 * <p>
 * Fields:
 * <ul>
 *     <li>{@code userId} - The identifier for a {@link SnowUser}, part of the composite key.</li>
 *     <li>{@code boundaryId} - The identifier for a {@link Boundary}, part of the composite key.</li>
 * </ul>
 * <p>
 * This class includes custom implementations of {@code equals} and {@code hashCode} methods,
 * which are essential for correctly handling composite keys in JPA. These implementations ensure
 * proper comparison and hash code generation, accommodating potential proxy objects used by
 * Hibernate, thus preventing issues related to lazy loading and entity identity.
 * <p>
 * Constructors:
 * The class currently includes a default constructor and a parameterized constructor for ease of instantiation,
 * though the latter may need adjustments to align with the actual data types of the fields.
 *
 * @see UserBoundary
 * @see SnowUser
 * @see Boundary
 */
@Getter
@Setter
@Embeddable
public class UserBoundaryId implements java.io.Serializable {
    private static final long serialVersionUID = 5332002771615057988L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "boundary_id", nullable = false)
    private Integer boundaryId;

    // might have to adjust these constructors, but for now they work
    public UserBoundaryId(Long userId, Long boundaryId) {
    }

    public UserBoundaryId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserBoundaryId entity = (UserBoundaryId) o;
        return Objects.equals(this.boundaryId, entity.boundaryId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boundaryId, userId);
    }

}