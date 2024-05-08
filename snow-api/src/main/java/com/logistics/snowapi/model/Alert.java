package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity class representing an alert as defined in the National Weather Service (NWS).
 * This class is linked to the 'alert' table in the database and includes various fields
 * that describe the alert's properties, such as its event type, onset and expiration times,
 * headline, and detailed description.
 * <p>
 * This class also manages relationships with {@link UgcZone} entities, indicating the geographic
 * zones affected by the alert. The relationship is modeled as a many-to-many association, managed
 * through the 'ugc_alert' join table. Additionally, it holds a one-to-many relationship with
 * {@link UgcAlert}, which tracks specific UGC zone alert details.
 * <p>
 * Attributes:
 * <ul>
 *     <li>{@code id} - The primary key of the alert.</li>
 *     <li>{@code event} - The type or name of the event triggering the alert.</li>
 *     <li>{@code onset} - The datetime when the alert becomes effective.</li>
 *     <li>{@code expires} - The datetime when the alert is no longer effective.</li>
 *     <li>{@code headline} - A brief headline summarizing the alert.</li>
 *     <li>{@code description} - A detailed description of the alert.</li>
 *     <li>{@code nwsID} - A unique identifier assigned by the National Weather Service.</li>
 *     <li>{@code ugcZones} - A set of {@link UgcZone} entities affected by this alert.</li>
 *     <li>{@code ugcAlerts} - A set of {@link UgcAlert} entities that provide additional details
 *                              related to the alert and its associated UGC zones.</li>
 * </ul>
 * Usage:
 * Instances of this class are used to persist and retrieve alert data from the database,
 * serve data in response to API requests, and manage alert-related operations within the application.
 *
 * @see UgcZone
 * @see UgcAlert
 */
@Getter
@Setter
@Entity
@Table(name = "alert")
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

    @ManyToMany
    @JoinTable(name = "ugc_alert",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "ugc_code"))
    private Set<UgcZone> ugcZones = new LinkedHashSet<>();

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UgcAlert> ugcAlerts = new HashSet<>();

}