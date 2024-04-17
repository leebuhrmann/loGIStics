package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "alert")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_id_generator")
    @SequenceGenerator(name = "alert_id_generator", sequenceName = "alert_alert_id_seq", allocationSize = 1)
    @Column(name = "alert_id", nullable = false)
    private Integer id;

    @Column(name = "event", length = 50)
    private String event;

    @Column(name = "onset")
    private OffsetDateTime onset;

    @Column(name = "expires")
    private OffsetDateTime expires;

    @Column(name = "headline", length = 150)
    private String headline;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "nws_id", length = 255)
    private String nwsID;

    @ManyToMany
    @JoinTable(name = "ugc_alert",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "ugc_code"))
    private Set<UgcZone> ugcZones = new LinkedHashSet<>();

}