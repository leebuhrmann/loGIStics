package com.logistics.snowapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.geojsonresponse.GeoJsonResponse;
import com.logistics.snowapi.repository.AlertRepository;
import com.logistics.snowapi.repository.UgcAlertRepository;
import com.logistics.snowapi.repository.UgcZoneRepository;
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

    @InjectMocks
    private NWSDataService nwsDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
//        restTemplate = restTemplateBuilder.build();
        nwsDataService = new NWSDataService(restTemplateBuilder, objectMapper, alertService, ugcZoneScraper, alertRepository, messagingTemplate, ugcZoneRepository, ugcAlertRepository);
    }

    @Test
    void FetchWeatherDataSuccessTest() throws JsonProcessingException {
        // Prepare a mock ResponseEntity with a dummy JSON string as the body
        String dummyJsonResponse = "{\"features\":[]}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(dummyJsonResponse, HttpStatus.OK);

        // Configure the mock to return this ResponseEntity when the getForEntity method is called
        when(restTemplate.getForEntity(any(), String.class)).thenReturn(mockResponse);

        // Optionally mock the objectMapper to return a GeoJsonResponse if your method processes the JSON
        GeoJsonResponse geoJsonResponse = new GeoJsonResponse();  // Assuming default constructor or setup method
        when(objectMapper.readValue(dummyJsonResponse, GeoJsonResponse.class)).thenReturn(geoJsonResponse);

        // Call the method under test
        nwsDataService.fetchWeatherData();

        // Verify that the restTemplate was called correctly
        verify(restTemplate).getForEntity(anyString(), eq(String.class));
        // Additional verifications can include checking that the GeoJsonResponse was processed if necessary
    }

    // Additional tests...
}

