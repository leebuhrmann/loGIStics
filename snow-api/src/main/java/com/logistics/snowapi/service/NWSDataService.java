package com.logistics.snowapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.geojsonresponse.Feature;
import com.logistics.snowapi.geojsonresponse.GeoJsonResponse;
import com.logistics.snowapi.model.Alert;
import com.logistics.snowapi.model.UgcAlert;
import com.logistics.snowapi.model.UgcAlertId;
import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.AlertRepository;
import com.logistics.snowapi.repository.UgcAlertRepository;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    private final UgcZoneRepository ugcZoneRepository;
    private final UgcAlertRepository ugcAlertRepository;
    private final UgcZoneScraper ugcZoneScraper;

    @Autowired
    public NWSDataService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, AlertService alertService, UgcZoneScraper ugcZoneScraper, AlertRepository alertRepository, SimpMessagingTemplate messagingTemplate, UgcZoneRepository ugcZoneRepository, UgcAlertRepository ugcAlertRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.alertService = alertService;
        this.ugcZoneScraper = ugcZoneScraper;
        this.alertRepository = alertRepository;
        this.messagingTemplate = messagingTemplate;
        this.ugcZoneRepository = ugcZoneRepository;
        this.ugcAlertRepository = ugcAlertRepository;
    }

    /**
     * Scheduled task that periodically fetches weather data from the National Weather Service (NWS) API every 60 seconds.
     * This method performs an HTTP GET request to the specified NWS API endpoint, retrieves weather alert data in GeoJSON format,
     * and then maps this data to a {@link GeoJsonResponse} object. The mapped data is subsequently processed to extract
     * individual alerts and their related information, which are then persisted in the database.
     * <p>
     * This method is annotated with {@code @Scheduled}, indicating that it is automatically run by the Spring framework at
     * fixed intervals (every 60 seconds in this case), making it a self-contained scheduled task for weather data retrieval.
     */
    @Scheduled(fixedRate = 60000) // runs every 60 seconds
    @CrossOrigin(origins = "/**")
    public void fetchWeatherData() {
        try {
            // GET request from NWS api
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // Maps the response into a POJO containing all the alert data.
            GeoJsonResponse geoJsonResponse = objectMapper.readValue(response.getBody(), GeoJsonResponse.class);
            processGeoJsonResponse(geoJsonResponse);
        }
        catch (RestClientException e) {
            System.out.printf("Failed to reach address: %s, Error: %s\n", url, e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.printf("Failed to parse response for address: %s\n", url);
            e.printStackTrace();
        }
    }

    /**
     * Processes a given {@link GeoJsonResponse} object containing weather alert information in GeoJSON format.
     * This method updates the database with new alerts and their associated geographic zone information.
     * It iterates over each feature (alert) in the GeoJSON response, converts each feature into an alert object,
     * and checks the database for its existence based on the alert's unique identifier. If the alert is new,
     * it is sent in real-time over a WebSocket channel, persisted in the database, and related geographic zone data
     * is scraped and also persisted.
     *
     * If no features (alerts) are present in the GeoJSON response, a log message is generated to indicate the absence
     * of current weather alerts.
     *
     * @param geoJsonResponse The {@link GeoJsonResponse} object containing weather alert data in GeoJSON format.
     *                        This object includes a list of features, where each feature represents a specific
     *                        weather alert with its associated data.
     */
    private void processGeoJsonResponse(GeoJsonResponse geoJsonResponse) {
        List<Feature> allFeatures = geoJsonResponse.getFeatures();
        if (!allFeatures.isEmpty()) {
            allFeatures.forEach(feature -> { // loop through every feature(alert)
                Alert alert = feature.getFeatureAsAlert();
                if (alert.getNwsID() != null && !alertRepository.existsByNwsID(alert.getNwsID())) { // checks if an alert is valid and if it already persists in the database
                    messagingTemplate.convertAndSend("/topic", feature); // TODO: needs logic to ensure that the alert belongs to a currently subscribed boundary

                    alert = alertService.createAlert(alert); // persist alert entry in database
                    ugcZoneScraper.scrape(feature.getProperties().getUgcCodeAddress()); // call ugc_zone scraper to ensure ugc_zone persistence in database
                    Alert finalAlert = alert; // required because lambdas are silly

                    feature.getProperties().getUgcCodeAddress().forEach(url -> { // loop through every ugc_zone related to this alert
                        String ugcCode = url.substring(url.lastIndexOf('/') + 1); // Extracts the last segment from the URL as the UGC code

                        System.out.println("Extracted UGC Code: " + ugcCode);  // Debug output
                        System.out.println("Processing alert event: " + feature.getProperties().getEvent()); // Debug output

                        UgcZone ugcZone = ugcZoneRepository.findByUgcCode(ugcCode).orElse(null);
                        if (ugcZone != null && finalAlert != null) { // check if the ugcZone and alert are not null
                            // Persist the ugc_alert entry in the database
                            UgcAlert ugcAlert = new UgcAlert();
                            UgcAlertId ugcAlertId = new UgcAlertId();
                            ugcAlertId.setUgcCode(ugcZone.getUgcCode());
                            ugcAlertId.setAlertId(finalAlert.getId());
                            ugcAlert.setId(ugcAlertId);
                            ugcAlert.setUgcCode(ugcZone);
                            ugcAlert.setAlert(finalAlert);

                            ugcAlertRepository.save(ugcAlert);
                            // End persist ugc_alert
                        } else {
                            System.out.println("Failed to save UgcAlert: UGC Zone or alert is null");
                        }
                    });
                }
            });
        } else {
            System.out.println("No current weather alerts.");
        }
    }
}