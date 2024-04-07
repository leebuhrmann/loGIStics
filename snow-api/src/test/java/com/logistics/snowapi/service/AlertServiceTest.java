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

import static org.junit.jupiter.api.Assertions.*;
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

    // Tests to see if the service saves and returns an alert
    @Test
    void createAlertSuccessTest() {
        // Set up the Alert object with necessary details, including the nwsID
        Alert newAlert = new Alert();
        newAlert.setEvent("Test Event");
        newAlert.setOnset(OffsetDateTime.now());
        newAlert.setExpires(OffsetDateTime.now().plusHours(1));
        newAlert.setHeadline("Test Headline");
        newAlert.setDescription("Test Description");
        newAlert.setNwsID("nws-test-id-123"); // tested field

        // Mock the repository's behavior to simulate that no existing alert has the same nwsID
        // Note: in simple terms, if .existsByNwsID is called with an argument matching newAlert.getNwsID() then return false.
        when(alertRepository.existsByNwsID(newAlert.getNwsID())).thenReturn(false);

        // Mock the repository's save method to return the alert itself upon saving
        // Note: if .save is called with any alert as an argument then return newAlert
        when(alertRepository.save(any(Alert.class))).thenReturn(newAlert);

        // Call the createAlert method with the new Alert that includes the nwsID
        Alert createdAlert = alertService.createAlert(newAlert);

        // Verify that the repository's existsByNwsID method was called exactly once with the specific nwsID
        verify(alertRepository, times(1)).existsByNwsID(newAlert.getNwsID());

        // Verify that the repository's save method was called exactly once with any Alert object
        verify(alertRepository, times(1)).save(any(Alert.class));

        // Assert that the created alert's nwsID matches the expected value
        assertNotNull(createdAlert, "The created alert should not be null.");
    }

    // Tests the service to ensure a new alert has a nws_id
    @Test
    void createAlertNoNwsIDTest() {

        Alert newAlert = new Alert();
        newAlert.setEvent("Test Event");
        newAlert.setOnset(OffsetDateTime.now());
        newAlert.setExpires(OffsetDateTime.now().plusHours(1));
        newAlert.setHeadline("Test Headline");
        newAlert.setDescription("Test Description");
        newAlert.setNwsID(null); // tested field

        // Mock the repository's behavior to simulate that no existing alert has the same nwsID
        // Note: in simple terms, if .existsByNwsID is called with an argument matching newAlert.getNwsID() then return false.
        when(alertRepository.existsByNwsID(newAlert.getNwsID())).thenReturn(false);

        // Mock the repository's save method to return the alert itself upon saving
        // Note: if .save is called with any alert as an argument then return newAlert
        when(alertRepository.save(any(Alert.class))).thenReturn(newAlert);

        // Call the createAlert method with the new Alert that includes the nwsID
        Alert createdAlert = alertService.createAlert(newAlert);

        // Verify that the repository's existsByNwsID method was called exactly once with the specific nwsID
        verify(alertRepository, times(0)).existsByNwsID(newAlert.getNwsID());

        // Verify that the repository's save method was called exactly once with any Alert object
        verify(alertRepository, times(0)).save(any(Alert.class));

        // Assert that the created alert's nwsID matches the expected value
        assertNull(createdAlert, "The created alert should be null.");
    }

    // Tests the service to ensure a new alert does not match an already stored alert
    @Test
    void createAlertNoIdenticalAlertsTest() {
        // Set up the Alert object with necessary details, including the nwsID
        Alert newAlert = new Alert();
        newAlert.setEvent("Test Event");
        newAlert.setOnset(OffsetDateTime.now());
        newAlert.setExpires(OffsetDateTime.now().plusHours(1));
        newAlert.setHeadline("Test Headline");
        newAlert.setDescription("Test Description");
        newAlert.setNwsID("nws-test-id-123"); // tested field

        // Mock the repository's behavior to simulate that no existing alert has the same nwsID
        // Note: in simple terms, if .existsByNwsID is called with an argument matching newAlert.getNwsID() then return false.
        when(alertRepository.existsByNwsID(newAlert.getNwsID())).thenReturn(true);

        // Mock the repository's save method to return the alert itself upon saving
        // Note: if .save is called with any alert as an argument then return newAlert
        when(alertRepository.save(any(Alert.class))).thenReturn(newAlert);

        // Call the createAlert method with the new Alert that includes the nwsID
        Alert createdAlert = alertService.createAlert(newAlert);

        // Verify that the repository's existsByNwsID method was called exactly once with the specific nwsID
        verify(alertRepository, times(1)).existsByNwsID(newAlert.getNwsID());

        // Verify that the repository's save method was called exactly once with any Alert object
        verify(alertRepository, times(0)).save(any(Alert.class));

        // Assert that the created alert's nwsID matches the expected value
        assertNull(createdAlert, "The created alert should be null.");
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
