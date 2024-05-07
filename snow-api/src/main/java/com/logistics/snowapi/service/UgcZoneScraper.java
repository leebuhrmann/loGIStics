package com.logistics.snowapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.ugczoneresponse.UgcZoneResponse;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.locationtech.jts.geom.MultiPolygon;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Service class for scraping geographic zone information related to UGC (Universal Geographic Code) zones.
 * This class handles the retrieval of data from the National Weather Service (NWS) and other sources,
 * parsing it, and persisting it in the database as {@link UgcZone} entities.
 * <p>
 * The class utilizes the {@link RestTemplate} to make HTTP requests, an {@link ObjectMapper} to parse the JSON
 * responses, and relies on {@link UgcZoneRepository} and {@link UgcZoneService} to interact with the database.
 * <p>
 * Usage:
 * This service is used to update the database with the latest UGC zone data from external sources. It checks for
 * the existence of UGC zone data before making a request to ensure that only missing or outdated data is updated.
 *
 * @see UgcZone
 * @see UgcZoneRepository
 * @see UgcZoneService
 */
@Service
public class UgcZoneScraper {

    private final RestTemplate restTemplate;
    private final UgcZoneRepository ugcZoneRepository;
    private final UgcZoneService ugcZoneService;
    private final ObjectMapper objectMapper;

    public UgcZoneScraper(RestTemplateBuilder restTemplateBuilder, UgcZoneRepository ugcZoneRepository, UgcZoneService ugcZoneService, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.ugcZoneRepository = ugcZoneRepository;
        this.ugcZoneService = ugcZoneService;
        this.objectMapper = objectMapper;
    }

    /**
     * Processes a list of URLs corresponding to UGC Zones by checking if their associated data
     * already exists within the database. For each URL whose data is not already present, this method performs a GET request
     * to retrieve geographic and zone information. The response is then parsed into a {@link UgcZoneResponse}, transformed into
     * a UGC Zone entity, and persisted in the database.
     * <p>
     * @param ugcCodeAddresses A list of URLs for UGC Zone data retrieval. Each URL is expected to end with a 6-character UGC code
     *                         that uniquely identifies a geographic zone.
     * @throws RestClientException If there are connectivity issues or errors returned by the server.
     * @throws IOException If there are issues parsing the JSON response.
     */
    public void scrape(ArrayList<String> ugcCodeAddresses) {
        for (String address : ugcCodeAddresses){
            String ugcCode = address.substring(address.length() - 6);
            if (!ugcZoneRepository.existsByUgcCode(ugcCode)) {
                // Performs a GET call on the UGCZone address.
                // Maps response to POJO
                // Creates UGCZone from POJO and stores in database
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);
                    UgcZoneResponse ugcZoneResponse = objectMapper.readValue(response.getBody(), UgcZoneResponse.class);
                    UgcZone ugcZone = createUgcZoneFromUgcZoneResponse(ugcCode, address, ugcZoneResponse);
                    ugcZoneService.createUgcZone(ugcZone);
                }
                catch (RestClientException e) {
                    System.out.printf("Failed to reach address: %s, UGC: %s, Error: %s\n", address, ugcCode, e.getMessage());
                    e.printStackTrace();
                }
                catch (IOException e) {
                    System.out.printf("Failed to parse response for address: %s, UGC Code: %s\n", address, ugcCode);
                    e.printStackTrace();
                }
            }
            else {
                System.out.printf("UgcZone %s already exists in database.\n", ugcCode);
            }
        }
    }

    /**
     * Constructs a {@link UgcZone} entity from the provided UGC code, UGC code address, and a {@link UgcZoneResponse}.
     * This method initializes a new {@code UgcZone} instance, setting its UGC code and address from the provided parameters,
     * and attempts to set the geometry based on the type specified in the {@code UgcZoneResponse}.
     * <p>
     * @param ugcCode The UGC code, extracted from the UGC code address, representing the unique identifier for the geographic zone.
     * @param ugcCodeAddress The full URL used to retrieve the UGC Zone information.
     * @param ugcZoneResponse A {@link UgcZoneResponse} object containing the response from a UGC data request.
     * @return A fully constructed {@code UgcZone} entity ready for persistence.
     * @throws IllegalStateException If the geometry type is neither 'Polygon' nor 'MultiPolygon', indicating an unsupported type.
     */
    private UgcZone createUgcZoneFromUgcZoneResponse(String ugcCode, String ugcCodeAddress, UgcZoneResponse ugcZoneResponse ) {
        UgcZone ugcZone = new UgcZone();
        ugcZone.setUgcCode(ugcCode);
        ugcZone.setUgcCodeAddress(ugcCodeAddress);
        if ("Polygon".equals(ugcZoneResponse.getType())) {
            ugcZone.setTheGeom(ugcZoneResponse.getGeometry());
        }
        else if ("MultiPolygon".equals(ugcZoneResponse.getType())) {
            ugcZone.setTheGeom(ugcZoneResponse.getGeometry());
        }
        else {
            System.out.printf("Geometry type mismatch for ugc_code: %s. Terminating process.\n", ugcCode);
//            System.exit(1);
        }
        return ugcZone;
    }
}