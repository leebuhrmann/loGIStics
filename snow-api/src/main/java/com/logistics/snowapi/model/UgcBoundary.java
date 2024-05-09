package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing an association between a UGC (Universal Geographic Code) zone and a geographic boundary.
 * This class is mapped to the 'ugc_boundary' table in the database. Each instance of this class represents a
 * relationship between a specific UGC zone and a boundary, utilizing a composite key for identification.
 * <p>
 * The {@link UgcBoundary} entity uses an embedded composite key represented by {@link UgcBoundaryId}, which includes
 * identifiers for both the UGC zone and the boundary. This structure facilitates efficient management of relationships
 * between geographic zones and boundaries, supporting complex queries and integrity constraints within the database.
 * <p>
 * Relationships:
 * <ul>
 *     <li>{@code ugcCode} - Many-to-one relationship with the {@link UgcZone} entity. Each {@code UgcBoundary} is linked
 *     to one {@link UgcZone}, represented by the {@code ugc_code} foreign key in the database.</li>
 *     <li>{@code boundary} - Many-to-one relationship with the {@link Boundary} entity. Each {@code UgcBoundary} is linked
 *     to one {@link Boundary}, represented by the {@code boundary_id} foreign key in the database.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are used within the application to maintain and query the relationships between UGC zones
 * and geographic boundaries. This helps in managing geographic data distribution, access control, and regional analysis
 * based on UGC classifications and boundary delineations.
 *
 * @see UgcBoundaryId
 * @see UgcZone
 * @see Boundary
 */
@Getter
@Setter
@Entity
@Table(name = "ugc_boundary")
public class UgcBoundary {
    @EmbeddedId
    private UgcBoundaryId id;

    @MapsId("ugcCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ugc_code", nullable = false)
    private UgcZone ugcCode;

    @MapsId("boundaryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boundary_id", nullable = false)
    private Boundary boundary;

}