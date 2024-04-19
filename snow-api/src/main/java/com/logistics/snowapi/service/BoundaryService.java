package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Boundary;
import com.logistics.snowapi.repository.BoundaryRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BoundaryService {

    private final BoundaryRepository boundaryRepository;
    private EntityManager entityManager;

    @Autowired
    public BoundaryService(BoundaryRepository boundaryRepository, EntityManager entityManager) {
        this.boundaryRepository = boundaryRepository;
        this.entityManager = entityManager;
    }

    public List<Boundary> findAllBoundaries() {
        return boundaryRepository.findAll();
    }

    public Optional<Boundary> findBoundaryById(Integer id) {
        return boundaryRepository.findById(id);
    }

    public Boundary createBoundary(Boundary boundary) {
        try {
            return boundaryRepository.save(boundary);
        } catch (Exception e) {
            System.out.println("Error saving boundary");
            throw e;
        }
    }

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

    public void deleteBoundary(Integer id) {
        boundaryRepository.deleteById(id);
    }

    private void copyBoundaryDetails(Boundary managedBoundary, Boundary boundary) {
        managedBoundary.setTheGeom(boundary.getTheGeom());
        managedBoundary.setDescription(boundary.getDescription());
        managedBoundary.setName(boundary.getName());
    }
}
