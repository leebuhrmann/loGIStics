package com.logistics.snowapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class UserBoundaryId implements java.io.Serializable {
    private static final long serialVersionUID = 5332002771615057988L;
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "boundary_id", nullable = false)
    private Integer boundaryId;

    // might have to adjust these constructors, but for now they work
    public UserBoundaryId(Long userId, Long boundaryId) {
    }

    public UserBoundaryId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserBoundaryId entity = (UserBoundaryId) o;
        return Objects.equals(this.boundaryId, entity.boundaryId) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boundaryId, userId);
    }

}