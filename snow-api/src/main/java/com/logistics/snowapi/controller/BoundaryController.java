package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.Boundary;
import com.logistics.snowapi.service.BoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Boundary} entities.
 * This controller provides endpoints for various CRUD operations related to geographic boundaries,
 * enabling the retrieval, creation, update, and deletion of boundary data via HTTP requests.
 * <p>
 * The controller is mapped to the base URL '/api/boundaries', from which all its endpoints are derived.
 * <p>
 * Methods:
 * <ul>
 *     <li>{@link #getAllBoundaries()} - Retrieves a list of all boundaries.</li>
 *     <li>{@link #getBoundaryById(Integer)} - Retrieves a specific boundary by its ID.</li>
 *     <li>{@link #createBoundary(Boundary)} - Creates a new boundary.</li>
 *     <li>{@link #updateBoundary(Integer, Boundary)} - Updates an existing boundary identified by ID.</li>
 *     <li>{@link #deleteBoundary(Integer)} - Deletes a boundary by its ID.</li>
 * </ul>
 *
 * @see BoundaryService
 */
@RestController
@RequestMapping("/api/boundaries")
public class BoundaryController {

    private final BoundaryService boundaryService;

    @Autowired
    public BoundaryController(BoundaryService boundaryService) {
        this.boundaryService = boundaryService;
    }


    /**
     * Endpoint to retrieve all boundaries.
     *
     * @return a list of all {@link Boundary} entities.
     */
    @GetMapping
    public List<Boundary> getAllBoundaries() {
        return boundaryService.findAllBoundaries();
    }

    /**
     * Endpoint to retrieve a single boundary by its ID.
     *
     * @param id the ID of the boundary to retrieve.
     * @return a {@link ResponseEntity} containing the found boundary, or a Not Found response if no boundary is found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Boundary> getBoundaryById(@PathVariable Integer id) {
        return boundaryService.findBoundaryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint to create a new boundary.
     *
     * @param boundary the {@link Boundary} to create.
     * @return the created boundary.
     */
    @PostMapping
    public Boundary createBoundary(@RequestBody Boundary boundary) {
        return boundaryService.createBoundary(boundary);
    }

    /**
     * Endpoint to update an existing boundary.
     *
     * @param id the ID of the boundary to update.
     * @param boundary the {@link Boundary} details to update.
     * @return a {@link ResponseEntity} with the updated boundary, or a Not Found response if the boundary could not be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Boundary> updateBoundary(@PathVariable Integer id, @RequestBody Boundary boundary) {
        boundary.setId(id); // Set the ID from the path to ensure consistency
        Boundary updatedBoundary = boundaryService.updateBoundary(boundary);
        if (updatedBoundary != null) {
            return ResponseEntity.ok(updatedBoundary);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/toggle-subscription/{id}")
    public ResponseEntity<Boundary> toggleBoundarySubscription(@PathVariable Integer id) {
        try {
            Boundary updatedBoundary = boundaryService.toggleBoundarySubscription(id);
            return ResponseEntity.ok(updatedBoundary);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint to delete a boundary by its ID.
     *
     * @param id the ID of the boundary to delete.
     * @return a {@link ResponseEntity} indicating success (200 OK) or failure (404 Not Found) based on whether the boundary was found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoundary(@PathVariable Integer id) {
        if (boundaryService.findBoundaryById(id).isPresent()) {
            boundaryService.deleteBoundary(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
