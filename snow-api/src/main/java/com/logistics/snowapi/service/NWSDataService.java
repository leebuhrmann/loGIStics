package com.logistics.snowapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.geojsonresponse.FeatureProperties;
import com.logistics.snowapi.geojsonresponse.GeoJsonResponse;
import com.logistics.snowapi.geojsonresponse.Feature;
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
    private final UgcZoneScraper ugcZoneScraper;

    // Constructor for RestTemplate injection
    public NWSDataService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, AlertService alertService, UgcZoneScraper ugcZoneScraper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.alertService = alertService;
        this.ugcZoneScraper = ugcZoneScraper;
    }

    /**
     * Performs a GET call on the NWS service and maps the response
     * to a POJO.
     */
//    @PostConstruct // ensures run on service initialization
    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    public void fetchWeatherData() {
        try {
            // GET request from NWS api
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Maps the response into a POJO containing all the alert data.
            GeoJsonResponse geoJsonResponse = objectMapper.readValue(response.getBody(), GeoJsonResponse.class);
            processGeoJsonResponse(geoJsonResponse);
        } catch (RestClientException | IOException e) {
            // TODO
            e.printStackTrace();
        }
    }

    /**
     * Processes the alerts(features) from GeoJasonReponse and PUTs the
     * alerts into the Database using the AlertService class.
     * @param geoJsonResponse
     */
    private void processGeoJsonResponse(GeoJsonResponse geoJsonResponse) {
        List<Feature> allFeatures = geoJsonResponse.getFeatures();
        if (!allFeatures.isEmpty()) {
            allFeatures.forEach(feature -> {
                System.out.println("processing alert event: " + feature.getProperties().getEvent());
//                System.out.println(feature.toString());
                // call zone scraper, which checks and adds ugc zones entries
                ugcZoneScraper.scrape(feature.getProperties().getUgcCodeAddress());
                // add alert entries
                alertService.createAlert(feature.getFeatureAsAlert());
                // add many-to-many entries (ugc-alert)
            });
        }
        else {
            System.out.println("No current weather alerts.");
        }
    }
}
