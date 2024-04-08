package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @Column(name = "visibility")
    private Integer visibility;

    @ManyToMany(mappedBy = "ugcZones")
    private Set<Alert> alerts = new LinkedHashSet<>();
    @ManyToMany(mappedBy = "ugcZones")
    private Set<Boundary> boundaries = new LinkedHashSet<>();

    @Column(name = "the_geom", columnDefinition = "geometry(MultiPolygon,2908)")
    private MultiPolygon theGeom;
}
