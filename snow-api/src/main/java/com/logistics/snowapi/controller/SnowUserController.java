package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.SnowUser;
import com.logistics.snowapi.service.SnowUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link SnowUser} entities.
 * This controller provides endpoints for various CRUD operations related to users,
 * enabling the retrieval, creation, update, and deletion of user data via HTTP requests.
 * <p>
 * The controller is mapped to the base URL '/api/users', from which all its endpoints are derived.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #getAllUsers()} - Retrieves a list of all users.</li>
 *     <li>{@link #getUserById(Integer)} - Retrieves a specific user by their ID.</li>
 *     <li>{@link #createUser(SnowUser)} - Creates a new user.</li>
 *     <li>{@link #updateUser(Integer, SnowUser)} - Updates an existing user identified by ID.</li>
 *     <li>{@link #deleteUser(Integer)} - Deletes a user by their ID.</li>
 * </ul>
 *
 * @see SnowUserService
 */
@RestController
@RequestMapping("/api/users")
public class SnowUserController {

    private final SnowUserService userService;

    @Autowired
    public SnowUserController(SnowUserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve all users.
     *
     * @return a list of all {@link SnowUser} entities.
     */
    @GetMapping
    public List<SnowUser> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Endpoint to retrieve a single user by their ID.
     *
     * @param id the ID of the user to retrieve.
     * @return a {@link ResponseEntity} containing the found user, or a Not Found response if no user is found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SnowUser> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new user.
     *
     * @param user the {@link SnowUser} to create.
     * @return the created user.
     */
    @PostMapping
    public SnowUser createUser(@RequestBody SnowUser user) {
        return userService.createUser(user);
    }

    /**
     * Endpoint to update an existing user.
     *
     * @param id the ID of the user to update.
     * @param user the {@link SnowUser} details to update.
     * @return a {@link ResponseEntity} with the updated user, or a Not Found response if the user could not be found or updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SnowUser> updateUser(@PathVariable Integer id, @RequestBody SnowUser user) {
        return userService.findUserById(id)
                .map(existingUser -> {
                    user.setId(id);
                    return ResponseEntity.ok(userService.updateUser(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to delete a user by their ID.
     *
     * @param id the ID of the user to delete.
     * @return a {@link ResponseEntity} indicating success (200 OK) or failure (404 Not Found) based on whether the user was found and deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(user -> {
                    userService.deleteUser(id);
                    return ResponseEntity.<Void>ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
