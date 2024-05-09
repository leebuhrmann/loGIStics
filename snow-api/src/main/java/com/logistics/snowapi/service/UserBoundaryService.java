package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import com.logistics.snowapi.repository.UserBoundaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that encapsulates the business logic for managing user-boundary associations.
 * This class provides methods to find, create, and delete {@link UserBoundary} entities, using the
 * {@link UserBoundaryRepository} for database interactions.
 * <p>
 * Methods in this class handle standard CRUD operations for user-boundary relationships and ensure
 * that these relationships are managed efficiently and consistently throughout the application.
 * <p>
 * Usage:
 * This service should be used whenever user-boundary associations need to be manipulated or queried.
 * It ensures that these interactions with the database are handled in a centralized manner,
 * promoting code reusability and separation of concerns.
 *
 * @see UserBoundary
 * @see UserBoundaryRepository
 */
@Service
public class UserBoundaryService {

    private final UserBoundaryRepository userBoundaryRepository;

    @Autowired
    public UserBoundaryService(UserBoundaryRepository userBoundaryRepository) {
        this.userBoundaryRepository = userBoundaryRepository;
    }

    /**
     * Retrieves all user-boundary associations stored in the database.
     *
     * @return a list of {@link UserBoundary} entities
     */
    public List<UserBoundary> findAll() {
        return userBoundaryRepository.findAll();
    }

    /**
     * Retrieves a user-boundary association by its composite ID.
     *
     * @param id the {@link UserBoundaryId} representing the composite key of the user-boundary association
     * @return an {@link Optional} of {@link UserBoundary} if found, or empty if no association is found
     */
    public Optional<UserBoundary> findById(UserBoundaryId id) {
        return userBoundaryRepository.findById(id);
    }

    /**
     * Saves a user-boundary association to the database, either updating an existing entry or creating a new one.
     *
     * @param userBoundary the {@link UserBoundary} entity to be saved
     * @return the saved user-boundary entity
     */
    public UserBoundary save(UserBoundary userBoundary) {
        return userBoundaryRepository.save(userBoundary);
    }

    /**
     * Deletes a user-boundary association from the database by its composite ID.
     *
     * @param id the {@link UserBoundaryId} representing the composite key of the user-boundary association to delete
     */
    public void delete(UserBoundaryId id) {
        userBoundaryRepository.deleteById(id);
    }

    /**
     * Creates a new user-boundary association in the database.
     *
     * @param userBoundary the {@link UserBoundary} entity to create
     * @return the newly created user-boundary entity
     */
    public UserBoundary createUserBoundary(UserBoundary userBoundary) {
        return save(userBoundary);
    }
}
