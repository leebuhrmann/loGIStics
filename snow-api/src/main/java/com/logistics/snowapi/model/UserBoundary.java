package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing the association between a user and a geographic boundary.
 * This class is linked to the 'user_boundary' table in the database and includes an embedded composite key
 * represented by {@link UserBoundaryId}. Each instance of this class establishes a relationship between a
 * specific user and a geographic boundary, indicating the boundaries that a user is associated with.
 * <p>
 * The {@link UserBoundary} entity uses the embedded composite key for identification and facilitates efficient
 * management of relationships between {@link SnowUser} and {@link Boundary} entities through many-to-one
 * relationships. This structure supports complex queries and integrity constraints within the database, ensuring
 * that relationships are properly maintained.
 * <p>
 * Relationships:
 * <ul>
 *     <li>{@code user} - Many-to-one relationship with the {@link SnowUser} entity. Each {@code UserBoundary} is linked
 *     to one {@link SnowUser}, represented by the {@code user_id} foreign key in the database.</li>
 *     <li>{@code boundary} - Many-to-one relationship with the {@link Boundary} entity. Each {@code UserBoundary} is linked
 *     to one {@link Boundary}, represented by the {@code boundary_id} foreign key in the database.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are used within the application to manage and query the relationships between users and
 * geographic boundaries, supporting functionalities such as personalized geographic data access and user-specific
 * geographic permissions.
 *
 * @see UserBoundaryId
 * @see SnowUser
 * @see Boundary
 */
@Getter
@Setter
@Entity
@Table(name = "user_boundary")
public class UserBoundary {
    @EmbeddedId
    private UserBoundaryId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private SnowUser user;

    @MapsId("boundaryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boundary_id", nullable = false)
    private Boundary boundary;

}