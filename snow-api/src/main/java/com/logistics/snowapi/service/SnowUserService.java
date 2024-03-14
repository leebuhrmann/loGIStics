package com.logistics.snowapi.service;

import com.logistics.snowapi.model.SnowUser;
import com.logistics.snowapi.repository.SnowUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnowUserService {

    private final SnowUserRepository userRepository;

    @Autowired
    public SnowUserService(SnowUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<SnowUser> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<SnowUser> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    public SnowUser createUser(SnowUser user) {
        return userRepository.save(user);
    }

    public SnowUser updateUser(SnowUser user) {
        // Ensure the user exists, handle appropriately if not
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
