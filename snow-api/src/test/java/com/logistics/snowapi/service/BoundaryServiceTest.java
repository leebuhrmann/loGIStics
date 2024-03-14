package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Boundary;
import com.logistics.snowapi.repository.BoundaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BoundaryServiceTest {

    @Mock
    private BoundaryRepository boundaryRepository;

    @InjectMocks
    private BoundaryService boundaryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBoundaryTest() {
        Boundary boundary = new Boundary();
        boundary.setId(1); // Assuming ID is set after saving

        when(boundaryRepository.save(any(Boundary.class))).thenReturn(boundary);

        Boundary createdBoundary = boundaryService.createBoundary(new Boundary());

        verify(boundaryRepository, times(1)).save(any(Boundary.class));
        assert createdBoundary.getId() != null;
    }

    @Test
    void findBoundaryByIdTest() {
        Integer boundaryId = 1;
        Optional<Boundary> optionalBoundary = Optional.of(new Boundary());
        when(boundaryRepository.findById(boundaryId)).thenReturn(optionalBoundary);

        Optional<Boundary> foundBoundary = boundaryService.findBoundaryById(boundaryId);

        verify(boundaryRepository, times(1)).findById(boundaryId);
        assert foundBoundary.isPresent();
    }

    @Test
    void deleteBoundaryTest() {
        Integer boundaryId = 1;
        doNothing().when(boundaryRepository).deleteById(anyInt());

        boundaryService.deleteBoundary(boundaryId);

        verify(boundaryRepository, times(1)).deleteById(boundaryId);
    }
}
