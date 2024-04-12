package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UgcZoneServiceTest {

    @Mock
    private UgcZoneRepository ugcZoneRepository;

    @InjectMocks
    private UgcZoneService ugcZoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUgcZoneTest() {
        UgcZone ugcZone = new UgcZone();
        ugcZone.setUgcCode("NEC001");
        ugcZone.setUgcCodeAddress("https://api.weather.gov/zones/county/NEC001");

        when(ugcZoneRepository.save(any(UgcZone.class))).thenReturn(ugcZone);

        UgcZone createdUgcZone = ugcZoneService.createUgcZone(new UgcZone());

        verify(ugcZoneRepository, times(1)).save(any(UgcZone.class));
    }

    @Test
    void findUgcZoneByUgcCodeTest() {
        String ugcCode = "NEC001";
        UgcZone ugcZone = new UgcZone();
        ugcZone.setUgcCode(ugcCode);
        ugcZone.setUgcCodeAddress("https://api.weather.gov/zones/county/NEC001");

        Optional<UgcZone> optionalUgcZone = Optional.of(ugcZone);
        when(ugcZoneRepository.findByUgcCode(ugcCode)).thenReturn(optionalUgcZone);

        Optional<UgcZone> foundBoundary = ugcZoneService.findUgcZoneByUgcCode(ugcCode);

        verify(ugcZoneRepository, times(1)).findByUgcCode(ugcCode);
        assert foundBoundary.isPresent();
    }

    @Test
    void deleteUgcZoneTest() {
        String ugcCode = "NEC001";
        doNothing().when(ugcZoneRepository).deleteByUgcCode(anyString());

        ugcZoneService.deleteUgcZoneByUgcCode(ugcCode);

        verify(ugcZoneRepository, times(1)).deleteByUgcCode(ugcCode);
    }
}