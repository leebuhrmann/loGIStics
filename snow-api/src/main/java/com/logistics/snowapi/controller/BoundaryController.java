package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.Boundary;
import com.logistics.snowapi.service.BoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boundaries")
public class BoundaryController {

    private final BoundaryService boundaryService;

    @Autowired
    public BoundaryController(BoundaryService boundaryService) {
        this.boundaryService = boundaryService;
    }

    @GetMapping
    public List<Boundary> getAllBoundaries() {
        return boundaryService.findAllBoundaries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boundary> getBoundaryById(@PathVariable Integer id) {
        return boundaryService.findBoundaryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Boundary createBoundary(@RequestBody Boundary boundary) {
        return boundaryService.createBoundary(boundary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boundary> updateBoundary(@PathVariable Integer id, @RequestBody Boundary boundary) {
        boundary.setId(id); // Set the ID from the path to ensure consistency
        Boundary updatedBoundary = boundaryService.updateBoundary(boundary);
        if (updatedBoundary != null) {
            return ResponseEntity.ok(updatedBoundary);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoundary(@PathVariable Integer id) {
        if (boundaryService.findBoundaryById(id).isPresent()) {
            boundaryService.deleteBoundary(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
