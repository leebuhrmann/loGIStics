package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import com.logistics.snowapi.service.UserBoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link UserBoundary} entities.
 * This controller provides endpoints for various CRUD operations related to user-boundary associations,
 * enabling the retrieval, creation, and deletion of these associations via HTTP requests.
 * <p>
 * The controller is mapped to the base URL '/api/userBoundaries', from which all its endpoints are derived.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #getAll()} - Retrieves a list of all user-boundary associations.</li>
 *     <li>{@link #getById(Long, Long)} - Retrieves a specific user-boundary association by composite key.</li>
 *     <li>{@link #create(UserBoundary)} - Creates a new user-boundary association.</li>
 *     <li>{@link #delete(Long, Long)} - Deletes a user-boundary association by its composite key.</li>
 * </ul>
 *
 * @see UserBoundaryService
 * @see UserBoundary
 * @see UserBoundaryId
 */
@RestController
@RequestMapping("/api/userBoundaries")
public class UserBoundaryController {

    private final UserBoundaryService userBoundaryService;

    @Autowired
    public UserBoundaryController(UserBoundaryService userBoundaryService) {
        this.userBoundaryService = userBoundaryService;
    }


    /**
     * Endpoint to retrieve all user-boundary associations.
     *
     * @return a list of all {@link UserBoundary} entities.
     */
    @GetMapping
    public List<UserBoundary> getAll() {
        return userBoundaryService.findAll();
    }

    /**
     * Endpoint to retrieve a single user-boundary association by its composite ID, consisting of a user ID and a boundary ID.
     *
     * @param userId the ID of the user part of the composite key.
     * @param boundaryId the ID of the boundary part of the composite key.
     * @return a {@link ResponseEntity} containing the found user-boundary association, or a Not Found response if no association is found.
     */
    @GetMapping("/{userId}/{boundaryId}")
    public ResponseEntity<UserBoundary> getById(@PathVariable Long userId, @PathVariable Long boundaryId) {
        return userBoundaryService.findById(new UserBoundaryId(userId, boundaryId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new user-boundary association.
     *
     * @param userBoundary the {@link UserBoundary} to create.
     * @return the created user-boundary association.
     */
    @PostMapping
    public UserBoundary create(@RequestBody UserBoundary userBoundary) {
        return userBoundaryService.save(userBoundary);
    }

    /**
     * Endpoint to delete a user-boundary association by its composite ID.
     *
     * @param userId the user ID part of the composite key.
     * @param boundaryId the boundary ID part of the composite key.
     * @return a {@link ResponseEntity} indicating success (200 OK) or failure (404 Not Found) based on whether the association was found and deleted.
     */
    @DeleteMapping("/{userId}/{boundaryId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long boundaryId) {
        UserBoundaryId id = new UserBoundaryId(userId, boundaryId);
        if (userBoundaryService.findById(id).isPresent()) {
            userBoundaryService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
