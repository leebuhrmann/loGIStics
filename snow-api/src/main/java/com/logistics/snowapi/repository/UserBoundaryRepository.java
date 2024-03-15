package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoundaryRepository extends JpaRepository<UserBoundary, UserBoundaryId> {
    // TBD
    // Custom query methods can be added here
}
