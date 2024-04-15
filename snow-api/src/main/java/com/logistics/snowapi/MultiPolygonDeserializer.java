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

public class MultiPolygonDeserializer extends JsonDeserializer<MultiPolygon> {

    private static final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public MultiPolygon deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        System.out.println("###################################################################################\nEntered Deserializer");
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = node.get("type").asText();
        JsonNode coordinates = node.get("coordinates");
//        System.out.printf("Type: %s\nCoordinates: %s\n", type, coordinates);

        if (!"MultiPolygon".equals(type)) {
            throw new IOException("Type mismatch: Expected MultiPolygon type");
        }

        return parseMultiPolygon(node);
    }

    private MultiPolygon parseMultiPolygon(JsonNode node) throws IOException {
        List<Polygon> polygons = new ArrayList<>();
        JsonNode coordinates = node.get("coordinates");

        if (coordinates == null || !coordinates.isArray()) {
            throw new IOException("Invalid coordinates data for MultiPolygon");
        }

        for (JsonNode polygonNode : coordinates) {
            polygons.add(parsePolygon(polygonNode));
        }
        System.out.println("we got here");
        MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygons.toArray(new Polygon[0]));
//        System.out.println(multiPolygon);
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

    private LinearRing parseLinearRing(JsonNode ringNode) throws IOException {
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
