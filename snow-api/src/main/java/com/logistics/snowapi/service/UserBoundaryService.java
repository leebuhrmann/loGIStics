package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import com.logistics.snowapi.repository.UserBoundaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBoundaryService {

    private final UserBoundaryRepository userBoundaryRepository;

    @Autowired
    public UserBoundaryService(UserBoundaryRepository userBoundaryRepository) {
        this.userBoundaryRepository = userBoundaryRepository;
    }

    public List<UserBoundary> findAll() {
        return userBoundaryRepository.findAll();
    }

    public Optional<UserBoundary> findById(UserBoundaryId id) {
        return userBoundaryRepository.findById(id);
    }

    public UserBoundary save(UserBoundary userBoundary) {
        return userBoundaryRepository.save(userBoundary);
    }

    public void delete(UserBoundaryId id) {
        userBoundaryRepository.deleteById(id);
    }

    public UserBoundary createUserBoundary(UserBoundary userBoundary) {
        return save(userBoundary);
    }
}
