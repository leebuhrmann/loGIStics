package com.logistics.snowapi.repository;

import com.logistics.snowapi.model.SnowUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link SnowUser} entities, extending {@link JpaRepository}.
 * This interface provides standard CRUD operations for managing SnowUser entities
 * within the database. It serves as a direct access point for all data retrieval and
 * manipulation operations on SnowUser entities, supporting functionalities throughout the application.
 * <p>
 * Usage:
 * This repository is used throughout the application to interact with the user data stored in the
 * database. It ensures that user data is accessed and managed efficiently, supporting a variety
 * of features that involve user data manipulation and querying.
 * </p>
 *
 * @see SnowUser
 */
public interface SnowUserRepository extends JpaRepository<SnowUser, Integer> {}
