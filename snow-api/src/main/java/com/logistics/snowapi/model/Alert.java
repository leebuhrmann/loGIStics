package com.logistics.snowapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "alert")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @JsonManagedReference
    @ManyToMany
    @JoinTable(name = "ugc_alert",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "ugc_code"))
    private Set<UgcZone> ugcZones = new LinkedHashSet<>();

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UgcAlert> ugcAlerts = new HashSet<>();

}