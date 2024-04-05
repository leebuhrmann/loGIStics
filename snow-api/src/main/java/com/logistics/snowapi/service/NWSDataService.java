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
            // Handle the error scenario
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
//                System.out.println(feature.toString());
                System.out.println("processing alert event: " + feature.getProperties().getEvent());

                // call zone scraper, which checks and adds ugc zones entries
                ugcZoneScraper.scrape(feature.getProperties().getUGC());
                // add alert entries
                alertService.createAlert(createAlertFromFeature(feature));
                // add many-to-many entries (ugc-alert)
            });
        }
        else {
            System.out.println("No current weather alerts.");
        }
    }

    /**
     * Converts a Feature object into an Alert object
     * @param feature
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
