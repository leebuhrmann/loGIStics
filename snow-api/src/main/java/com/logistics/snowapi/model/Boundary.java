package com.logistics.snowapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.logistics.snowapi.MultiPolygonDeserializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.LinkedHashSet;
import java.util.Set;

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
    @JsonDeserialize(using = MultiPolygonDeserializer.class)
    private MultiPolygon theGeom;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Column(name = "name", nullable = true, length = 255)
    private String name;

    @ManyToMany
    @JoinTable(name = "ugc_boundary",
            joinColumns = @JoinColumn(name = "boundary_id"),
            inverseJoinColumns = @JoinColumn(name = "ugc_code"))
//    @JsonManagedReference
    private Set<UgcZone> ugcZones = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_boundary",
            joinColumns = @JoinColumn(name = "boundary_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    @JsonBackReference
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