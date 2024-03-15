package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "spatial_ref_sys")
public class SpatialRefSy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "srid", nullable = false)
    private Integer id;

    @Column(name = "auth_name", length = 256)
    private String authName;

    @Column(name = "auth_srid")
    private Integer authSrid;

    @Column(name = "srtext", length = 2048)
    private String srtext;

    @Column(name = "proj4text", length = 2048)
    private String proj4text;

}