package com.logistics.snowapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.locationtech.jts.geom.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom deserializer for converting JSON data representing a multi-polygon into a {@link MultiPolygon} object.
 * This deserializer handles the JSON structure specific to multi-polygon geometries, ensuring that they
 * are correctly parsed into JTS's {@link MultiPolygon} format.
 * <p>
 * The JSON data must contain a "type" field with the value "MultiPolygon" and a "coordinates" field that
 * lists the sets of coordinate arrays for each polygon. Each polygon must define an outer boundary and
 * can optionally define one or more inner boundaries (holes).
 * <p>
 * Usage:
 * This deserializer is typically used in conjunction with Jackson annotations to directly map JSON data
 * onto {@link MultiPolygon} fields in model classes.
 *
 * Example:
 * <pre>{@code
 * @JsonDeserialize(using = MultiPolygonDeserializer.class)
 * private MultiPolygon theGeom;
 * }</pre>
 *
 * @see MultiPolygon
 * @see GeometryFactory
 */
public class MultiPolygonDeserializer extends JsonDeserializer<MultiPolygon> {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public MultiPolygon deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = node.get("type").asText();

        if (!"MultiPolygon".equals(type)) {
            throw new IOException("Type mismatch: Expected MultiPolygon type");
        }

        return parseMultiPolygon(node);
    }

    /**
     * Parses the JSON node containing the multi-polygon geometry data.
     * This method first validates the presence of a "type" field that matches "MultiPolygon".
     * It then iterates over the "coordinates" field to construct each polygon before combining them into a multi-polygon.
     *
     * @param node The JSON node containing the multi-polygon data.
     * @return A {@link MultiPolygon} representing the geometry described by the input JSON.
     * @throws IOException if the input JSON is malformed or does not represent a valid multi-polygon.
     */
    private MultiPolygon parseMultiPolygon(JsonNode node) throws IOException {
        List<Polygon> polygons = new ArrayList<>();
        JsonNode coordinates = node.get("coordinates");

        if (coordinates == null || !coordinates.isArray()) {
            throw new IOException("Invalid coordinates data for MultiPolygon");
        }

        for (JsonNode polygonNode : coordinates) {
            polygons.add(parsePolygon(polygonNode));
        }
        MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons.toArray(new Polygon[0]));
        return multiPolygon;
    }

    private Polygon parsePolygon(JsonNode polygonNode) throws IOException {
        if (polygonNode.size() == 0 || !polygonNode.isArray()) {
            throw new IOException("Invalid polygon data");
        }

        LinearRing shell = parseLinearRing(polygonNode.get(0));
        LinearRing[] holes = new LinearRing[polygonNode.size() - 1];

        for (int i = 1; i < polygonNode.size(); i++) {
            holes[i - 1] = parseLinearRing(polygonNode.get(i));
        }

        return geometryFactory.createPolygon(shell, holes);
    }

    private LinearRing parseLinearRing(JsonNode ringNode) {
        Coordinate[] coordinates = new Coordinate[ringNode.size()];

        for (int i = 0; i < ringNode.size(); i++) {
            JsonNode coord = ringNode.get(i);
            double x = coord.get(0).asDouble();
            double y = coord.get(1).asDouble();
            coordinates[i] = new Coordinate(x, y);
        }

        return geometryFactory.createLinearRing(coordinates);
    }
}
