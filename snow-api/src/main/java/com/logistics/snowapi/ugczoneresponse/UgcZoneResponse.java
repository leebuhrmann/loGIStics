package com.logistics.snowapi.ugczoneresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

//import java.awt.*;
import java.util.Map;
import java.util.List;

import org.locationtech.jts.geom.*;

/**
 * Represents the response structure for geographic zone data, particularly UGC (Universal Geographic Code) zones.
 * This class is capable of parsing JSON representations of either polygon or multi-polygon geometries and converting
 * them into JTS (Java Topology Suite) {@link MultiPolygon} objects.
 * <p>
 * The {@code UgcZoneResponse} class uses a custom deserializer method annotated with {@link JsonProperty} to process
 * the 'geometry' field in the incoming JSON. This method handles the conversion of nested JSON coordinate arrays into
 * JTS geometry objects, supporting both 'Polygon' and 'MultiPolygon' types.
 * <p>
 * Attributes:
 * <ul>
 *   <li>{@code type} - The type of geometry ('Polygon' or 'MultiPolygon').</li>
 *   <li>{@code geometry} - The JTS {@link MultiPolygon} object representing the geographic shape of the UGC zone.</li>
 *   <li>{@code geometryFactory} - A {@link GeometryFactory} instance for creating JTS geometry objects.</li>
 * </ul>
 * <p>
 * Usage:
 * This class is typically used to deserialize JSON data from external geographic data services that provide
 * UGC zone information. The custom deserializer ensures that both simple polygons and more complex multi-polygon
 * structures are accurately represented as JTS geometry objects, facilitating further geographic operations
 * within the application.
 *
 * @see MultiPolygon
 * @see GeometryFactory
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UgcZoneResponse {
    private String type;
    private MultiPolygon geometry;
    private GeometryFactory geometryFactory = new GeometryFactory();

    @JsonProperty("geometry")
    private void geoMapper(Map<String,Object> geometryMap) {
        ObjectMapper mapper = new ObjectMapper();
        type = (String) geometryMap.get("type");
        Object coordinates = geometryMap.get("coordinates");

        try {
            if ("Polygon".equals(type)) {
                List<List<List<Double>>> coords = mapper.convertValue(coordinates, new TypeReference<List<List<List<Double>>>>() {});
                Polygon singlePolygon = convertToPolygon(coords, geometryFactory);
                this.geometry = geometryFactory.createMultiPolygon(new Polygon[]{singlePolygon});
            } else if ("MultiPolygon".equals(type)) {
                List<List<List<List<Double>>>> coords = mapper.convertValue(coordinates, new TypeReference<List<List<List<List<Double>>>>>() {});
                this.geometry = convertToMultiPolygon(coords, geometryFactory);
            } else {
                System.out.println("Unsupported geometry type: " + type);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error casting coordinates: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Coordinate[] toCoordinateArray(List<List<Double>> coordinates) {
        return coordinates.stream()
                .map(coord -> new Coordinate(coord.get(0), coord.get(1)))
                .toArray(Coordinate[]::new);
    }

    private Polygon convertToPolygon(List<List<List<Double>>> polygonCoords, GeometryFactory geometryFactory) {
        // The first list of coordinates represents the exterior boundary of the polygon
        Coordinate[] exteriorCoordinates = toCoordinateArray(polygonCoords.get(0));
        LinearRing exteriorRing = geometryFactory.createLinearRing(exteriorCoordinates);

        // Subsequent lists of coordinates represent interior boundaries (holes)
        LinearRing[] holes = polygonCoords.stream()
                .skip(1) // Skip the exterior ring
                .map(coords -> geometryFactory.createLinearRing(toCoordinateArray(coords)))
                .toArray(LinearRing[]::new);

        return geometryFactory.createPolygon(exteriorRing, holes);
    }

    private MultiPolygon convertToMultiPolygon(List<List<List<List<Double>>>> multiPolygonCoords, GeometryFactory geometryFactory) {
        Polygon[] polygons = multiPolygonCoords.stream()
                .map(polygonCoords -> convertToPolygon(polygonCoords, geometryFactory))
                .toArray(Polygon[]::new);

        return geometryFactory.createMultiPolygon(polygons);
    }

}
