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
     * Processes a given {@link GeoJsonResponse} object, which contains weather alert information in GeoJSON format,
     * and updates the database accordingly. This method iterates over each feature (alert) in the GeoJSON response,
     * performs necessary pre-processing, and then uses application services to persist the alerts and their related
     * geographic zone information to the database.
     * <p>
     * If the GeoJSON response does not contain any features (alerts), it logs a message indicating that there are no current
     * weather alerts.
     *
     * @param geoJsonResponse The {@link GeoJsonResponse} object containing weather alert data in GeoJSON format to be
     *                        processed. This object includes a list of features, where each feature represents a specific
     *                        weather alert with its associated data.
     */
    private void processGeoJsonResponse(GeoJsonResponse geoJsonResponse) {
        List<Feature> allFeatures = geoJsonResponse.getFeatures();
        if (!allFeatures.isEmpty()) {
            allFeatures.forEach(feature -> {
                feature.getProperties().getUgcCodeAddress().forEach(url -> {
                    // Extracts the UGC code from the URL
                    String ugcCode = extractUgcCodeFromUrl(url);
                    System.out.println("Extracted UGC Code: " + ugcCode);  // Debug output
                    System.out.println("Processing alert event: " + feature.getProperties().getEvent());
                    Alert alert = feature.getFeatureAsAlert();
                    messagingTemplate.convertAndSend("/topic", feature); //this should be moved down I think

                    if (alert.getNwsID() != null && !alertRepository.existsByNwsID(alert.getNwsID())) {
                        ugcZoneScraper.scrape(feature.getProperties().getUgcCodeAddress());

                        alert = alertService.createAlert(alert);

                        UgcZone ugcZone = ugcZoneRepository.findByUgcCode(ugcCode).orElse(null);

                        if (ugcZone != null && alert != null) {
                            UgcAlert ugcAlert = new UgcAlert();
                            UgcAlertId ugcAlertId = new UgcAlertId(); // Instantiates the composite key

                            ugcAlertId.setUgcCode(ugcZone.getUgcCode()); // Sets the UGC code
                            ugcAlertId.setAlertId(alert.getId()); // Sets the alert ID

                            ugcAlert.setId(ugcAlertId); // Sets the composite ID in UgcAlert
                            ugcAlert.setUgcCode(ugcZone); // Associates UGC Zone
                            ugcAlert.setAlert(alert); // Associates Alert

                            ugcAlertRepository.save(ugcAlert); // Saves the UgcAlert entity
                        } else {
                            System.out.println("Failed to save UgcAlert: UGC Zone or alert is null");
                        }
                    }
                });
            });
        } else {
            System.out.println("No current weather alerts.");
        }
    }

    private String extractUgcCodeFromUrl(String url) {
        // Extracts the last segment from the URL as the UGC code
        return url.substring(url.lastIndexOf('/') + 1);
    }
}