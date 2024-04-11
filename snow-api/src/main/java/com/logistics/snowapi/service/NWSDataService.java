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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.repository.AlertRepository;

import java.io.IOException;
import java.util.List;

/**
 * Service class for retrieving alert data from NWS and sending alerts through
 * the WebSocket.
 */
@Service
public class NWSDataService {
    @Value("${nwsalert.api.url}")
    private String url;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final AlertService alertService;
    private final AlertRepository alertRepository;
    private final SimpMessagingTemplate messagingTemplate;

    // Constructor for RestTemplate injection
    @Autowired
    public NWSDataService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, AlertService alertService,
            AlertRepository alertRepository, SimpMessagingTemplate messagingTemplate) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.alertService = alertService;
        this.alertRepository = alertRepository;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Performs a GET call on the NWS service and maps the response
     * to a POJO.
     */
    @PostConstruct // ensures run on service initialization
    @Scheduled(fixedRate = 60000)
    @CrossOrigin(origins = "/**")
    public void fetchWeatherData() {
        try {
            // GET request from NWS api
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Maps the response into a POJO containing all the alert data.
            GeoJsonResponse geoJsonResponse = objectMapper.readValue(response.getBody(), GeoJsonResponse.class);
            processGeoJsonResponse(geoJsonResponse);
        } catch (RestClientException | IOException e) {
            // Handle the error scenario
            e.printStackTrace();
        }
    }

    /**
     * Processes the alerts(features) from GeoJasonReponse.
     * PUTs the new alerts into the Database using the AlertService and sends them
     * through the WebSocket.
     * 
     * @param geoJsonResponse The {@link GeoJsonResponse} to be processed.
     */
    private void processGeoJsonResponse(GeoJsonResponse geoJsonResponse) {
        List<Feature> allFeatures = geoJsonResponse.getFeatures();
        if (!allFeatures.isEmpty()) {
            allFeatures.forEach(feature -> {
                System.out.println("processing alert event: " + feature.getProperties().getEvent());
                Alert alert = createAlertFromFeature(feature);

                // This line should be moved into the if statement below to only send new alerts
                // through the frontend.
                messagingTemplate.convertAndSend("/topic", feature);

                if (alert.getNwsID() != null && !alertRepository.existsByNwsID(alert.getNwsID())) {
                    alertService.createAlert(alert);
                }
            });
        } else {
            System.out.println("No current weather alerts.");
        }
    }

    /**
     * Converts a Feature object into an Alert object
     * 
     * @param feature The {@link Feature} to be converted to an {@link Alert}.
     * @return
     */
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
