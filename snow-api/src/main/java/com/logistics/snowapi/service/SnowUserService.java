package com.logistics.snowapi.service;

import com.logistics.snowapi.model.SnowUser;
import com.logistics.snowapi.repository.SnowUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class that encapsulates the business logic for managing user data.
 * This class provides methods to find, create, update, and delete {@link SnowUser} entities,
 * utilizing the {@link SnowUserRepository} for database interactions.
 * <p>
 * Methods in this class handle standard CRUD operations, ensuring that user data is managed
 * efficiently and consistently throughout the application. This includes operations like
 * retrieving all users, finding a specific user by ID, adding new users, updating existing
 * user records, and deleting users.
 * <p>
 * Usage:
 * This service should be used whenever user entities need to be manipulated or queried.
 * It ensures that user interactions with the database are handled in a centralized manner,
 * promoting code reusability and separation of concerns.
 *
 * @see SnowUser
 * @see SnowUserRepository
 */
@Service
public class SnowUserService {

    private final SnowUserRepository userRepository;

    @Autowired
    public SnowUserService(SnowUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all user entities stored in the database.
     *
     * @return a list of {@link SnowUser} entities
     */
    public List<SnowUser> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return an {@link Optional} containing the found user or empty if no user is found
     */
    public Optional<SnowUser> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the {@link SnowUser} entity to create
     * @return the newly created user
     */
    public SnowUser createUser(SnowUser user) {
        return userRepository.save(user);
    }

    /**
     * Updates an existing user in the database. This method presumes the existence of the user
     * as it does not explicitly handle the case where the user may not exist.
     *
     * @param user the {@link SnowUser} entity to update
     * @return the updated user
     */
    public SnowUser updateUser(SnowUser user) {
        // Ensure the user exists, handle appropriately if not
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the database by their ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
