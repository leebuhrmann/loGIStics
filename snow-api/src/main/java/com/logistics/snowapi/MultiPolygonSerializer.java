package com.logistics.snowapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.*;

import java.io.IOException;

/**
 * Custom serializer for converting {@link MultiPolygon} objects into a JSON format compatible with GeoJSON.
 * This serializer ensures that the complex structure of multi-polygon geometries is accurately represented
 * in JSON, making it suitable for web applications and APIs that consume GeoJSON data.
 * <p>
 * The JSON output includes a "type" field with the value "MultiPolygon" and a "coordinates" field that
 * details the arrays of coordinates for each polygon. Each polygon is defined by an outer boundary and
 * optionally by one or more inner boundaries (holes).
 * <p>
 * Usage:
 * This serializer is typically used in conjunction with Jackson annotations to directly map {@link MultiPolygon}
 * fields in model classes to JSON.
 *
 * Example:
 * <pre>{@code
 * @JsonSerialize(using = MultiPolygonSerializer.class)
 * private MultiPolygon theGeom;
 * }</pre>
 *
 * @see MultiPolygon
 */
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

    /**
     * Writes the coordinates for a single polygon, including its exterior boundary and any interior boundaries (holes),
     * to the provided {@link JsonGenerator}.
     *
     * @param polygon The {@link Polygon} object whose coordinates are being written.
     * @param gen The {@link JsonGenerator} used to write the JSON structure.
     * @throws IOException if there are issues writing to the {@link JsonGenerator}.
     */
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

    /**
     * Writes a series of {@link Coordinate} objects to the provided {@link JsonGenerator}.
     * This method formats each coordinate into a JSON array structure.
     *
     * @param coordinates An array of {@link Coordinate} to be written.
     * @param gen The {@link JsonGenerator} used to output the JSON structure.
     * @throws IOException if there are issues writing to the {@link JsonGenerator}.
     */
    private void writeCoordinates(Coordinate[] coordinates, JsonGenerator gen) throws IOException {
        for (Coordinate coord : coordinates) {
            gen.writeStartArray();
            gen.writeNumber(coord.x);
            gen.writeNumber(coord.y);
            gen.writeEndArray();
        }
    }
}

