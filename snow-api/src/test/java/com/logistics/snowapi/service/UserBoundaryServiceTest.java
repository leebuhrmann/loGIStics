package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UserBoundary;
import com.logistics.snowapi.model.UserBoundaryId;
import com.logistics.snowapi.repository.UserBoundaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserBoundaryServiceTest {

    @Mock
    private UserBoundaryRepository userBoundaryRepository;

    @InjectMocks
    private UserBoundaryService userBoundaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserBoundaryTest() {
        UserBoundaryId id = new UserBoundaryId(1L, 1L); // Assuming constructor UserBoundaryId(userId, boundaryId)
        UserBoundary userBoundary = new UserBoundary();
        userBoundary.setId(id);

        when(userBoundaryRepository.save(any(UserBoundary.class))).thenReturn(userBoundary);

        UserBoundary created = userBoundaryService.createUserBoundary(new UserBoundary());

        assertThat(created).isNotNull();
        verify(userBoundaryRepository, times(1)).save(any(UserBoundary.class));
    }


    @Test
    void deleteUserBoundaryTest() {
        UserBoundaryId id = new UserBoundaryId(1L, 1L);
        doNothing().when(userBoundaryRepository).deleteById(id);

        userBoundaryService.delete(id);

        verify(userBoundaryRepository, times(1)).deleteById(id);
    }
}