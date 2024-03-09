package com.logistics.snowapi.service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NWSDataService {

    private final RestTemplate restTemplate;

    // Constructor for RestTemplate injection
    public NWSDataService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @PostConstruct // ensures run on service initialization
    @Scheduled(fixedRate = 60000)
    public void fetchWeatherData() {
        String url = "https://api.weather.gov/";
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); // GET request from NWS api

            // Parse the JSON response and set your variables
            // Assuming you have a method to parse the JSON and set variables
            parseAndSetData(response.getBody());
        } catch (RestClientException e) {
            // Handle the error scenario
            e.printStackTrace();
        }
    }

    private void parseAndSetData(String jsonData) {
        // Implement your JSON parsing logic here
        System.out.println(jsonData);
    }
}
