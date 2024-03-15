package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import com.logistics.snowapi.service.UserBoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userBoundaries")
public class UserBoundaryController {

    private final UserBoundaryService userBoundaryService;

    @Autowired
    public UserBoundaryController(UserBoundaryService userBoundaryService) {
        this.userBoundaryService = userBoundaryService;
    }

    @GetMapping
    public List<UserBoundary> getAll() {
        return userBoundaryService.findAll();
    }

    @GetMapping("/{userId}/{boundaryId}")
    public ResponseEntity<UserBoundary> getById(@PathVariable Long userId, @PathVariable Long boundaryId) {
        return userBoundaryService.findById(new UserBoundaryId(userId, boundaryId))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserBoundary create(@RequestBody UserBoundary userBoundary) {
        return userBoundaryService.save(userBoundary);
    }

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
