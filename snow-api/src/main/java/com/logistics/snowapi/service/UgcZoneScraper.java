package com.logistics.snowapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.ugczoneresponse.UgcZoneResponse;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

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
     * Processes a list of URLs corresponding to UGC (Universal Geographic Code) Zones by checking if their associated data
     * already exists within the database. For each URL whose data is not already present, this method performs a GET request
     * to retrieve geographic and zone information from the National Weather Service (NWS). The response is then parsed,
     * transformed into a UGC Zone entity, and persisted in the database.
     * <p>
     *
     * @param ugcCodeAddresses A {@code ArrayList<String>} containing the URLs for UGC Zone data retrieval. Each URL is expected
     *                         to end with a 6-character UGC code that uniquely identifies a geographic zone.
     * @throws RestClientException If there is an issue with the RESTful request, including connection problems or errors returned
     *                             by the target server.
     * @throws IOException If there is an error parsing the response from the GET request into a {@code UgcZoneResponse} object.
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
                    System.out.printf("Failed to reach address: %s, UGC: %s, Error: %s", address, ugcCode, e.getMessage());
                    e.printStackTrace();
                }
                catch (IOException e) {
                    System.out.printf("Failed to parse response for address: %s, UGC Code: %s", address, ugcCode);
                    e.printStackTrace();
                }
            }
            else {
                System.out.printf("UgcZone %s already exists in database.", ugcCode);
            }
        }
    }

    /**
     * Constructs a {@link UgcZone} entity from the provided UGC code, UGC code address, and a {@link UgcZoneResponse} object.
     * This method initializes a new {@code UgcZone} instance, setting its UGC code and address from the provided parameters.
     * It then attempts to set the geometry of the {@code UgcZone} based on the geometry type (e.g., Polygon, MultiPolygon) specified
     * in the {@code UgcZoneResponse}.
     * <p>
     * The geometry from the {@code UgcZoneResponse} is assigned to the {@code UgcZone} based on its type. Currently, this method
     * supports 'Polygon' and 'MultiPolygon' types. If the geometry type does not match these supported types, the method prints
     * an error message and terminates the process.
     * <p>
     *
     * @param ugcCode The UGC code as a {@code String}, extracted from the UGC code address, representing the unique identifier
     *                for the geographic zone.
     * @param ugcCodeAddress The full URL address used to retrieve the UGC Zone information, serving as a reference or source URL.
     * @param ugcZoneResponse A {@link UgcZoneResponse} object containing the response from a UGC data request, which includes
     *                        type and geometry information for a UGC zone.
     * @return A fully constructed {@code UgcZone} entity ready to be persisted to the database, with UGC code, UGC code address,
     *         and geometry set according to the response data.
     * @throws IllegalStateException If the geometry type in {@code UgcZoneResponse} is neither 'Polygon' nor 'MultiPolygon',
     *                               indicating an unsupported or unknown geometry type that prevents the successful creation
     *                               of a {@code UgcZone} entity. This exception will terminate the process.
     */
    private UgcZone createUgcZoneFromUgcZoneResponse(String ugcCode, String ugcCodeAddress, UgcZoneResponse ugcZoneResponse ) {
        UgcZone ugcZone = new UgcZone();
        ugcZone.setUgcCode(ugcCode);
        ugcZone.setUgcCodeAddress(ugcCodeAddress);
        // TODO: set the ugcZone geometry before being persisted in db
        if ("Polygon".equals(ugcZoneResponse.getType())) {
//            ugcZone.setGeometry(ugcZoneResponse.getGeometry().getCoordinates());
        }
        else if ("MultiPolygon".equals(ugcZoneResponse.getType())) {
//            ugcZone.setGeometry(ugcZoneResponse.getGeometry().getCoordinates());
        }
        else {
            System.out.println("Geometry type mismatch. Terminating process.");
            System.exit(1);
        }
        return ugcZone;
    }
}