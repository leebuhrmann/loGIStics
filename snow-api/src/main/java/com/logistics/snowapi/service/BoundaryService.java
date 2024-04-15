package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Boundary;
import com.logistics.snowapi.repository.BoundaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoundaryService {

    private final BoundaryRepository boundaryRepository;

    @Autowired
    public BoundaryService(BoundaryRepository boundaryRepository) {
        this.boundaryRepository = boundaryRepository;
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
            throw e;  // Ensure the exception is properly handled or logged
        }
    }


    public Boundary updateBoundary(Boundary boundary) {
        if (boundary.getId() != null && boundaryRepository.existsById(boundary.getId())) {
            return boundaryRepository.save(boundary);
        }
        // Handle the case where the boundary does not exist
        return null;
    }

    public void deleteBoundary(Integer id) {
        boundaryRepository.deleteById(id);
    }
}
