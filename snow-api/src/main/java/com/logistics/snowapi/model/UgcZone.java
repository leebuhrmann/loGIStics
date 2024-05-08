package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity class representing a UGC (Universal Geographic Code) zone within the system.
 * This class is linked to the 'ugc_zone' table in the database and includes several properties
 * that define the characteristics of a geographic zone, such as its UGC code, the URL address for the
 * UGC code, and its geometric representation.
 * <p>
 * The {@link UgcZone} entity manages relationships with {@link Alert} and {@link Boundary} entities,
 * indicating which alerts and boundaries are associated with this UGC zone. This is achieved through
 * many-to-many relationships, facilitating the association of multiple zones with multiple alerts and
 * boundaries. Additionally, the entity has a one-to-many relationship with {@link UgcAlert}, representing
 * specific alert details related to this UGC zone.
 * <p>
 * Attributes:
 * <ul>
 *     <li>{@code ugcCode} - The unique identifier for the UGC zone, used as the primary key.</li>
 *     <li>{@code ugcCodeAddress} - The URL address providing more information about the UGC zone.</li>
 *     <li>{@code theGeom} - The geometric representation of the UGC zone, stored as a {@link MultiPolygon}.</li>
 *     <li>{@code alerts} - A set of {@link Alert} entities associated with this UGC zone.</li>
 *     <li>{@code boundaries} - A set of {@link Boundary} entities associated with this UGC zone.</li>
 *     <li>{@code ugcAlerts} - A set of {@link UgcAlert} entities that provide additional details about alerts related to this UGC zone.</li>
 * </ul>
 * <p>
 * Usage:
 * Instances of this class are used to manage geographic zone data within the application, supporting functionalities
 * such as alert distribution based on geographic zones, geographic data management, and region-specific access controls.
 *
 * @see Alert
 * @see Boundary
 * @see UgcAlert
 * @see MultiPolygon
 */
@Getter
@Setter
@Entity
@Table(name = "ugc_zone")
public class UgcZone {
    @Id
    @Column(name = "ugc_code", nullable = false, length = 6)
    private String ugcCode;

    @Column(name = "ugc_code_address", length = 200)
    private String ugcCodeAddress;

    @Column(columnDefinition = "geometry(MultiPolygon,3857)")
    private MultiPolygon theGeom;

    @ManyToMany(mappedBy = "ugcZones")
    private Set<Alert> alerts = new LinkedHashSet<>();
    @ManyToMany(mappedBy = "ugcZones")
    private Set<Boundary> boundaries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ugcCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UgcAlert> ugcAlerts = new HashSet<>();
}