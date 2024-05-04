package com.logistics.snowapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.geojsonresponse.Feature;
import com.logistics.snowapi.geojsonresponse.FeatureProperties;
import com.logistics.snowapi.geojsonresponse.GeoJsonResponse;
import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.model.UgcAlert;
import com.logistics.snowapi.model.UgcAlertId;
import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.AlertRepository;
import com.logistics.snowapi.repository.UgcAlertRepository;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.locationtech.jts.geom.MultiPolygon;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

class NWSDataServiceTest {

    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AlertService alertService;
    @Mock
    private AlertRepository alertRepository;
    @Mock
    private SimpMessagingTemplate messagingTemplate;
    @Mock
    private UgcZoneRepository ugcZoneRepository;
    @Mock
    private UgcAlertRepository ugcAlertRepository;
    @Mock
    private UgcZoneScraper ugcZoneScraper;

    @Mock
    private Feature feature;

    @InjectMocks
    private NWSDataService nwsDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        restTemplate = restTemplateBuilder.build();
        nwsDataService = new NWSDataService(restTemplateBuilder, objectMapper, alertService, ugcZoneScraper, alertRepository, messagingTemplate, ugcZoneRepository, ugcAlertRepository);
    }

    @Test
    void ProcessGeoJsonSuccessTest() {
        // Setting up Objects
        String event = "Unit Test Event";
        OffsetDateTime onset = OffsetDateTime.now();
        OffsetDateTime expires = OffsetDateTime.now().plusHours(1);
        String headline = "Unit Test Headline";
        String description = "Unit Test Description";
        String ugcCodeAddress = "Unit Test UGC Code Address";
        ArrayList<String> dummyUgcCodeAddresses = new ArrayList<>();
        dummyUgcCodeAddresses.add(ugcCodeAddress);
        String nwsID = "Unit Test NWS Feature ID";
        int alertID = 1;
        String ugcCode = "Unit Test UGC code";

        FeatureProperties dummyProperty = new FeatureProperties();
        dummyProperty.setEvent(event);
        dummyProperty.setOnset(onset);
        dummyProperty.setExpires(expires);
        dummyProperty.setHeadline(headline);
        dummyProperty.setDescription(description);
        dummyProperty.setUgcCodeAddress(dummyUgcCodeAddresses);

        Feature dummyFeature = new Feature();
        dummyFeature.setId(nwsID);
        dummyFeature.setProperties(dummyProperty);

        GeoJsonResponse dummyResponse = new GeoJsonResponse();
        ArrayList<Feature> dummyFeatures = new ArrayList<>();
        dummyFeatures.add(dummyFeature);
        dummyResponse.setFeatures(dummyFeatures);

        Alert dummyAlert = new Alert();
        dummyAlert.setId(alertID);
        dummyAlert.setEvent(event);
        dummyAlert.setOnset(onset);
        dummyAlert.setExpires(expires);
        dummyAlert.setHeadline(headline);
        dummyAlert.setDescription(description);
        dummyAlert.setNwsID(nwsID);

        UgcZone dummyUgcZone = new UgcZone();
        dummyUgcZone.setUgcCode(ugcCode);
        dummyUgcZone.setUgcCodeAddress(ugcCodeAddress);
        dummyUgcZone.setTheGeom(null);

        UgcAlertId dummyUgcAlertId = new UgcAlertId();
        dummyUgcAlertId.setUgcCode(ugcCode);
        dummyUgcAlertId.setAlertId(alertID);

        UgcAlert dummyUgcAlert = new UgcAlert();
        dummyUgcAlert.setId(dummyUgcAlertId);
        dummyUgcAlert.setUgcCode(dummyUgcZone);
        dummyUgcAlert.setAlert(dummyAlert);
        // End setup Objects

        doNothing().when(ugcZoneScraper).scrape(any());
        doNothing().when(messagingTemplate).convertAndSend(any());

        when(feature.getFeatureAsAlert()).thenReturn(dummyAlert);
        when(alertRepository.existsByNwsID(dummyAlert.getNwsID())).thenReturn(false);
        when(alertService.createAlert(any(Alert.class))).thenReturn(dummyAlert);
        when(ugcZoneRepository.findByUgcCode(any())).thenReturn(Optional.of(dummyUgcZone));
        when(ugcAlertRepository.save(any())).thenReturn(dummyUgcAlert);

        nwsDataService.processGeoJsonResponse(dummyResponse);

        verify(messagingTemplate, times(1)).convertAndSend("/topic", dummyFeature);
        verify(alertService, times(1)).createAlert(any(Alert.class));
        verify(ugcZoneScraper, times(1)).scrape(dummyUgcCodeAddresses);
        verify(ugcAlertRepository, times(1)).save(any());
    }

//    @Test
//    void FetchWeatherDataSuccessTest() throws JsonProcessingException {
//        // Prepare a mock ResponseEntity with a dummy JSON string as the body
//        String dummyJsonResponse = "{\"features\":[]}";
//        ResponseEntity<String> mockResponse = new ResponseEntity<>(dummyJsonResponse, HttpStatus.OK);
//
//        // Configure the mock to return this ResponseEntity when the getForEntity method is called
//        when(restTemplate.getForEntity(any(), eq(String.class))).thenReturn(mockResponse);
//
//        // Optionally mock the objectMapper to return a GeoJsonResponse if your method processes the JSON
//        GeoJsonResponse geoJsonResponse = new GeoJsonResponse();  // Assuming default constructor or setup method
//        when(objectMapper.readValue(dummyJsonResponse, GeoJsonResponse.class)).thenReturn(geoJsonResponse);
//
//        // Call the method under test
//        nwsDataService.fetchWeatherData();
//
//        // Verify that the restTemplate was called correctly
//        verify(restTemplate).getForEntity(anyString(), eq(String.class));
//        // Additional verifications can include checking that the GeoJsonResponse was processed if necessary
//    }

    // Additional tests...
}

