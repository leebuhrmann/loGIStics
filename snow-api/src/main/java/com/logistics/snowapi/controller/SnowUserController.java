package com.logistics.snowapi.controller;

import com.logistics.snowapi.model.SnowUser;
import com.logistics.snowapi.service.SnowUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class SnowUserController {

    private final SnowUserService userService;

    @Autowired
    public SnowUserController(SnowUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<SnowUser> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SnowUser> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SnowUser createUser(@RequestBody SnowUser user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SnowUser> updateUser(@PathVariable Integer id, @RequestBody SnowUser user) {
        return userService.findUserById(id)
                .map(existingUser -> {
                    user.setId(id);
                    return ResponseEntity.ok(userService.updateUser(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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
