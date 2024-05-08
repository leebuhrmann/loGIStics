package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link UserBoundary} entities, extending {@link JpaRepository}.
 * This interface facilitates database operations on user-boundary associations, providing
 * standard CRUD operations.
 * <p>
 * The {@code UserBoundaryRepository} is designed to handle interactions with the database
 * for managing associations between users and geographic boundaries. Each association is uniquely
 * identified by a composite key represented by {@link UserBoundaryId}.
 * <p>
 * Usage:
 * This repository is crucial for operations that involve linking users to specific geographic
 * boundaries. It provides essential data access functionalities to other components of the application
 * that deal with permissions, accessibility, and management of geographic data in relation to user profiles.
 * <p>
 * Future enhancements could include custom query methods for finding all boundaries associated with a
 * particular user, or all users associated with a specific boundary, depending on application requirements.
 *
 * @see UserBoundary
 * @see UserBoundaryId
 */
public interface UserBoundaryRepository extends JpaRepository<UserBoundary, UserBoundaryId> {}
