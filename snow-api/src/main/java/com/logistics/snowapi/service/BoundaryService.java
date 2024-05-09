package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Boundary;
import com.logistics.snowapi.repository.BoundaryRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class that encapsulates the business logic for managing geographic boundaries.
 * This class provides methods to find, create, update, and delete {@link Boundary} entities,
 * utilizing the {@link BoundaryRepository} for database interactions and the {@link EntityManager}
 * for transactional data management.
 * <p>
 * Methods in this class handle standard CRUD operations and additional logic to ensure that the
 * boundary data is consistent and integrates well with the database operations, especially during updates
 * which may involve complex transactional scenarios.
 * <p>
 * Usage:
 * This service is used whenever geographic boundaries need to be managed within the system. It ensures
 * that boundaries are handled consistently, with proper transaction management to maintain data integrity.
 *
 * @see Boundary
 * @see BoundaryRepository
 */
@Service
public class BoundaryService {

    private final BoundaryRepository boundaryRepository;
    private EntityManager entityManager;

    @Autowired
    public BoundaryService(BoundaryRepository boundaryRepository, EntityManager entityManager) {
        this.boundaryRepository = boundaryRepository;
        this.entityManager = entityManager;
    }

    /**
     * Retrieves all boundary entities from the database.
     *
     * @return a list of {@link Boundary} entities
     */
    public List<Boundary> findAllBoundaries() {
        return boundaryRepository.findAll();
    }

    /**
     * Retrieves a boundary by its ID.
     *
     * @param id the ID of the boundary to retrieve
     * @return an {@link Optional} of {@link Boundary} if found, or empty if no boundary is found
     */
    public Optional<Boundary> findBoundaryById(Integer id) {
        return boundaryRepository.findById(id);
    }

    /**
     * Creates a new boundary in the database.
     *
     * @param boundary the {@link Boundary} entity to create
     * @return the persisted boundary
     * @throws RuntimeException if there is an error during saving, encapsulated in a generic exception
     */
    public Boundary createBoundary(Boundary boundary) {
        try {
            return boundaryRepository.save(boundary);
        } catch (Exception e) {
            System.out.println("Error saving boundary");
            throw e;
        }
    }

    /**
     * Updates an existing boundary in the database. This method handles transactional state changes
     * to ensure the boundary data is correctly updated and synchronized with the current database state.
     *
     * @param boundary the {@link Boundary} entity to update
     * @return the updated and managed boundary entity
     * @throws IllegalStateException if the boundary is not found in the database
     */
    @Transactional
    public Boundary updateBoundary(Boundary boundary) {
        if (boundary.getId() != null && boundaryRepository.existsById(boundary.getId())) {
            Boundary managedBoundary = boundaryRepository.findById(boundary.getId())
                    .orElseThrow(() -> new IllegalStateException("Boundary not found")); // Better to throw an exception if the entity doesn't exist
            copyBoundaryDetails(managedBoundary, boundary);
            boundaryRepository.save(managedBoundary);
            entityManager.flush(); // Ensure all changes are persisted
            return managedBoundary;
        }
        throw new IllegalStateException("Boundary not found"); // Throw exception if the boundary doesn't exist
    }


    /**
     * Deletes a boundary from the database by its ID.
     *
     * @param id the ID of the boundary to delete
     */
    public void deleteBoundary(Integer id) {
        boundaryRepository.deleteById(id);
    }

    public Boundary toggleBoundarySubscription(Integer id) {
        Boundary boundary = boundaryRepository.findById(id).orElseThrow(() -> new RuntimeException("Boundary not found"));
        boundary.setSubscribed(!boundary.isSubscribed());  // Toggle the subscribed status
        return boundaryRepository.save(boundary);
    }
    /**
     * Helper method to copy details from the source {@link Boundary} object to the destination managed
     * {@link Boundary} object. This method is used during the update process to ensure that all relevant
     * boundary details are correctly propagated to the managed entity before saving.
     *
     * @param managedBoundary the managed {@link Boundary} entity that will receive the updates
     * @param boundary the source {@link Boundary} entity containing the updated details
     */
    private void copyBoundaryDetails(Boundary managedBoundary, Boundary boundary) {
        managedBoundary.setTheGeom(boundary.getTheGeom());
        managedBoundary.setDescription(boundary.getDescription());
        managedBoundary.setName(boundary.getName());
    }
}
