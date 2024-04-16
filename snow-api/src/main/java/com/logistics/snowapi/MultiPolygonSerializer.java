package com.logistics.snowapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.*;

import java.io.IOException;

public class MultiPolygonSerializer extends JsonSerializer<MultiPolygon> {
    @Override
    public void serialize(MultiPolygon value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Simplify or accurately convert MultiPolygon to a GeoJSON-like structure
        gen.writeStartObject();
        gen.writeStringField("type", "MultiPolygon");
        gen.writeArrayFieldStart("coordinates");

        for (int i = 0; i < value.getNumGeometries(); i++) {
            Polygon polygon = (Polygon) value.getGeometryN(i);
            gen.writeStartArray();
            writePolygonCoordinates(polygon, gen);
            gen.writeEndArray();
        }

        gen.writeEndArray();
        gen.writeEndObject();
    }

    private void writePolygonCoordinates(Polygon polygon, JsonGenerator gen) throws IOException {
        gen.writeStartArray(); // Start exterior ring
        writeCoordinates(polygon.getExteriorRing().getCoordinates(), gen);
        gen.writeEndArray(); // End exterior ring

        for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
            gen.writeStartArray(); // Start interior ring
            writeCoordinates(polygon.getInteriorRingN(i).getCoordinates(), gen);
            gen.writeEndArray(); // End interior ring
        }
    }

    private void writeCoordinates(Coordinate[] coordinates, JsonGenerator gen) throws IOException {
        for (Coordinate coord : coordinates) {
            gen.writeStartArray();
            gen.writeNumber(coord.x);
            gen.writeNumber(coord.y);
            gen.writeEndArray();
        }
    }
}

