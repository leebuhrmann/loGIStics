package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UgcZone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Commit
public class UgcZoneServiceIntegrationTest {

    @Autowired
    private UgcZoneService ugcZoneService;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Test
    public void testCreateUgcZoneWithCoordinates() {
        // Sample dataset
        List<List<Double[]>> polygonsCoordinates = new ArrayList<>();
        List<Double[]> polygonCoordinates = List.of(
                new Double[]{-94.856796200000005, 31.512811599999999},
                new Double[]{-94.8543935, 31.510912299999998},
                new Double[]{-94.851799, 31.511112199999996},
                new Double[]{-94.852195699999996, 31.508712699999997},
                new Double[]{-94.856796200000005, 31.512811599999999} // Close the loop
        );
        polygonsCoordinates.add(polygonCoordinates);

        // Create and save the UgcZone
        UgcZone savedZone = ugcZoneService.createOrUpdateUgcZone("TEST01", "Test Address", 1, polygonsCoordinates);

        // Retrieve and assert
        UgcZone retrievedZone = ugcZoneService.findUgcZoneById("TEST01").orElse(null);
        assertNotNull(retrievedZone, "Retrieved UgcZone should not be null");

        // More assertions can be added to check the contents of the retrieved entity
    }
}
