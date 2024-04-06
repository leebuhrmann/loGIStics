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

    public void scrape(ArrayList<String> ugcCodeAddresses) {
//        String address = ugcCodeAddresses.getFirst();
//        try {
//            ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);
//            UgcZoneResponse ugcZoneResponse = objectMapper.readValue(response.getBody(), UgcZoneResponse.class);
//            System.out.println("coords: " + ugcZoneResponse.getGeometry().getCoordinates());
//        }
//        catch (RestClientException | IOException e) {
//            e.printStackTrace();
//        }
        for (String address : ugcCodeAddresses){
            String ugcCode = address.substring(address.length() - 6);
            if (!ugcZoneRepository.existsByUgcCode(ugcCode)) {
                // grab ugc zone geom
                try {
                    ResponseEntity<String> response = restTemplate.getForEntity(address, String.class);
                    UgcZoneResponse ugcZoneResponse = objectMapper.readValue(response.getBody(), UgcZoneResponse.class);
                    // assign geom to ugcZone
                    UgcZone ugcZone = createUgcZoneFromUgcZoneResponse(ugcCode, address, ugcZoneResponse);
                    //create ugcZone in db
                    UgcZone test = ugcZoneService.createUgcZone(ugcZone);
                    System.out.printf("UgcZone was created: [%s, %s]\n", test.getUgcCode(), test.getUgcCodeAddress());
                }
                catch (RestClientException | IOException e) {
                    // TODO
                    e.printStackTrace();
                }
            }
            else {
                System.out.printf("UgcZone %s already exists in database.\n", ugcCode);
            }
        }
    }

    private UgcZone createUgcZoneFromUgcZoneResponse(String ugcCode, String ugcCodeAddress, UgcZoneResponse ugcZoneResponse ) {
        UgcZone ugcZone = new UgcZone();
        ugcZone.setUgcCode(ugcCode);
        ugcZone.setUgcCodeAddress(ugcCodeAddress);
        System.out.println("proof of logic: " + ugcZoneResponse.getGeometry().getCoordinates());//TODO currently only prints the geom and does not assign it. Waiting for Arber's branch.

        return ugcZone;
    }
}