package com.logistics.snowapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.logistics.snowapi.MultiPolygonDeserializer;
import com.logistics.snowapi.MultiPolygonSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a geographic boundary within the system, which is stored as a multi-polygon geometry.
 * This class includes serialization and deserialization functionalities to handle GeoJSON multi-polygon
 * geometry using Jackson and JTS (Java Topology Suite).
 * <p>
 * The {@code Boundary} class is annotated as an entity with the {@code @Entity} annotation, meaning
 * it is mapped to a database table named "boundary". Each boundary has an ID, a description, and a name,
 * along with associated {@code UgcZone} and {@code SnowUser} through many-to-many relationships.
 * <p>
 * Attributes:
 * <ul>
 *   <li>{@code id} - The primary key of the boundary entity.</li>
 *   <li>{@code theGeom} - The multi-polygon geometry of the boundary, handled by custom serializers
 *       and deserializers to integrate with the JSON format.</li>
 *   <li>{@code description} - An optional description of the boundary.</li>
 *   <li>{@code name} - An optional name of the boundary.</li>
 *   <li>{@code ugcZones} - A set of {@code UgcZone} entities associated with this boundary.</li>
 *   <li>{@code snowUsers} - A set of {@code SnowUser} entities associated with this boundary.</li>
 * </ul>
 * The multi-polygon geometry is mapped to the database with specific spatial data definitions, ensuring
 * compatibility with spatial databases.
 */
@Getter
@Setter
@Entity
@Table(name = "boundary")
public class Boundary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boundary_id", nullable = false)
    private Integer id;

    @Column(columnDefinition = "geometry(MultiPolygon,3857)")
    @JsonProperty("the_geom")
    @JsonDeserialize(using = MultiPolygonDeserializer.class) // deserialize the incoming GeoJson into a JTS MultiPolyong
    @JsonSerialize(using = MultiPolygonSerializer.class) // reserialize the JTS MultiPolygon back into Json format to be persisted in the database
    private MultiPolygon theGeom;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @Column(name = "subscribed", nullable = false)  // Add this line to include the subscribed field
    private boolean subscribed;

    @ManyToMany
    @JoinTable(name = "ugc_boundary",
            joinColumns = @JoinColumn(name = "boundary_id"),
            inverseJoinColumns = @JoinColumn(name = "ugc_code"))
    @JsonIgnore
    private Set<UgcZone> ugcZones = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_boundary",
            joinColumns = @JoinColumn(name = "boundary_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<SnowUser> snowUsers = new LinkedHashSet<>();


/*
 TODO create field to map the 'the_geom' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "the_geom", columnDefinition = "geometry")
    private Object theGeom;
*/
    /* another possibility
@Type(type = "org.hibernate.spatial.GeometryType")
@Column(name = "the_geom", columnDefinition = "geometry(Point,4326)")
private Point theGeom;
     */
}