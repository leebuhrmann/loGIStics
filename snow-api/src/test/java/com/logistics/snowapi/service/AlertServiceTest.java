package com.logistics.snowapi.service;

import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.AlertRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlertServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private AlertService alertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAlertTest() {
        Alert alert = new Alert();
        alert.setId(1);
        alert.setEvent("Test Event");
        alert.setOnset(OffsetDateTime.now());
        alert.setExpires(OffsetDateTime.now().plusHours(1));
        alert.setHeadline("Test Headline");
        alert.setDescription("Test Description");

        when(alertRepository.save(any(Alert.class))).thenReturn(alert);

        Alert createdAlert = alertService.createAlert(new Alert());

        verify(alertRepository, times(1)).save(any(Alert.class));
        assert createdAlert.getId() != null;
    }

    @Test
    void deleteAlertTest() {
        Integer alertId = 1;
        doNothing().when(alertRepository).deleteById(anyInt());

        alertService.deleteAlert(alertId);

        verify(alertRepository, times(1)).deleteById(alertId);
    }

    @Test
    void findAlertByIdTest() {
        Integer alertId = 1;
        Optional<Alert> optionalAlert = Optional.of(new Alert());
        when(alertRepository.findById(alertId)).thenReturn(optionalAlert);

        Optional<Alert> foundAlert = alertService.findAlertById(alertId);

        verify(alertRepository, times(1)).findById(alertId);
        assert foundAlert.isPresent();
    }
}
