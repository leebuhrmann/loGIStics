package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

import java.util.HashSet;
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

    @Column(columnDefinition = "geometry(MultiPolygon,3857)")
    private MultiPolygon theGeom;

    @ManyToMany(mappedBy = "ugcZones")
    private Set<Alert> alerts = new LinkedHashSet<>();
    @ManyToMany(mappedBy = "ugcZones")
    private Set<Boundary> boundaries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "ugcCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UgcAlert> ugcAlerts = new HashSet<>();
}