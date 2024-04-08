package com.logistics.snowapi.service;

import com.logistics.snowapi.model.UgcZone;
import com.logistics.snowapi.repository.UgcZoneRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LinearRing;
import java.util.List;
import java.util.Optional;

@Service
public class UgcZoneService {

    private final UgcZoneRepository ugcZoneRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Autowired
    public UgcZoneService(UgcZoneRepository ugcZoneRepository) {
        this.ugcZoneRepository = ugcZoneRepository;
    }


    public UgcZone createOrUpdateUgcZone(String ugcCode, String ugcCodeAddress, Integer visibility, List<List<Double[]>> polygonsCoordinates) {
        UgcZone ugcZone = ugcZoneRepository.findById(ugcCode)
                .orElse(new UgcZone());

        ugcZone.setUgcCode(ugcCode);
        ugcZone.setUgcCodeAddress(ugcCodeAddress);
        ugcZone.setVisibility(visibility);

        // Convert coordinates to MultiPolygon and set to the_geom
        MultiPolygon theGeom = this.coordinatesToMultiPolygon(polygonsCoordinates);
        ugcZone.setTheGeom(theGeom);

        return ugcZoneRepository.save(ugcZone);
    }

    // Add the findUgcZoneById method

    public Optional<UgcZone> findUgcZoneById(String ugcCode) {
        return ugcZoneRepository.findById(ugcCode);
    }

    private MultiPolygon coordinatesToMultiPolygon(List<List<Double[]>> polygonsCoordinates) {
        Polygon[] polygons = new Polygon[polygonsCoordinates.size()];

        for (int i = 0; i < polygonsCoordinates.size(); i++) {
            List<Double[]> polygonCoordinates = polygonsCoordinates.get(i);
            Coordinate[] coordinates = new Coordinate[polygonCoordinates.size()];

            for (int j = 0; j < polygonCoordinates.size(); j++) {
                Double[] point = polygonCoordinates.get(j);
                coordinates[j] = new Coordinate(point[0], point[1]);
            }

            LinearRing shell = geometryFactory.createLinearRing(coordinates);
            polygons[i] = geometryFactory.createPolygon(shell, null);
        }

        return geometryFactory.createMultiPolygon(polygons);
    }
}
