package com.logistics.snowapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.geojsonresponse.FeatureProperties;
import com.logistics.snowapi.geojsonresponse.GeoJsonResponse;
import com.logistics.snowapi.geojsonresponse.Feature;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import com.logistics.snowapi.model.Alert;

import java.io.IOException;
import java.util.List;

@Service
public class NWSDataService {
    @Value("${nwsalert.api.url}")
    private String url;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AlertService alertService;

    // Constructor for RestTemplate injection
    public NWSDataService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, AlertService alertService) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.alertService = alertService;
    }

    @PostConstruct // ensures run on service initialization
    @Scheduled(fixedRate = 60000)
    public void fetchWeatherData() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); // GET request from NWS api

            GeoJsonResponse geoJsonResponse = objectMapper.readValue(response.getBody(), GeoJsonResponse.class);

            processGeoJsonResponse(geoJsonResponse);
        } catch (RestClientException | IOException e) {
            // Handle the error scenario
            e.printStackTrace();
        }
    }

    private void processGeoJsonResponse(GeoJsonResponse geoJsonResponse) {
        // Implement your logic to work with the GeoJsonResponse object
        // For example, iterating over the features and printing some properties
        List<Feature> allFeatures = geoJsonResponse.getFeatures();
        if (!allFeatures.isEmpty()) {
            allFeatures.forEach(feature -> {
//                System.out.println(feature.toString());
                System.out.println("processing alert event: " + feature.getProperties().getEvent());
                alertService.createAlert(createAlertFromFeature(feature));
            });
        }
        else {
            System.out.println("No current weather alerts.");
        }
    }

    private Alert createAlertFromFeature(Feature feature) {
        Alert alert = new Alert();
        FeatureProperties properties = feature.getProperties();
        alert.setEvent(properties.getEvent());
        alert.setOnset(properties.getOnset());
        alert.setExpires(properties.getExpires());
        alert.setHeadline(properties.getHeadline());
        alert.setDescription(properties.getDescription());
        alert.setNwsID(feature.getId());

        return alert;
    }
}
