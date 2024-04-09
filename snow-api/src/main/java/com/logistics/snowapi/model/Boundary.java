package com.logistics.snowapi.model;

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
    private MultiPolygon theGeom;

    @ManyToMany
    @JoinTable(name = "ugc_boundary",
            joinColumns = @JoinColumn(name = "boundary_id"),
            inverseJoinColumns = @JoinColumn(name = "ugc_code"))
    private Set<UgcZone> ugcZones = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_boundary",
            joinColumns = @JoinColumn(name = "boundary_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
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