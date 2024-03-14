package com.logistics.snowapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ugc_boundary")
public class UgcBoundary {
    @EmbeddedId
    private UgcBoundaryId id;

    @MapsId("ugcCode")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ugc_code", nullable = false)
    private UgcZone ugcCode;

    @MapsId("boundaryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "boundary_id", nullable = false)
    private Boundary boundary;

}